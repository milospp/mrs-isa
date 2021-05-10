package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.model.dto.MedicineSearchDTO;
import isa9.Farmacy.model.dto.PharmacySearchDTO;
import isa9.Farmacy.model.dto.PriceInPharmaciesDTO;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.RatingService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public abstract class MedicineServiceBase implements MedicineService {

    protected PharmacyService pharmacyService;
    protected UserService userService;
    protected RatingService ratingService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @Autowired
    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Override
    public Medicine updateMedicineRating(Medicine medicine) {
        double rating = ratingService.getMedicineAverage(medicine);
        medicine.setRating(rating);
        return save(medicine);
    }

    @Override
    public List<Medicine> filterMedicines(List<Medicine> medicines, MedicineSearchDTO medicineSearchDTO) {
        if (medicineSearchDTO == null) return medicines;
        final Map<String, Comparator<Medicine>> critMap = new HashMap<String, Comparator<Medicine>>();
        critMap.put("NAME_ASC", (o1,o2)->{return o1.getName().trim().compareToIgnoreCase(o2.getName().trim()); });
        critMap.put("NAME_DES", (o2,o1)->{return o1.getName().trim().compareToIgnoreCase(o2.getName().trim()); });
        critMap.put("SHAPE_ASC", (o1,o2)->{return o1.getShape().trim().compareToIgnoreCase(o2.getShape().trim()); });
        critMap.put("SHAPE_DES", (o2,o1)->{return o1.getShape().trim().compareToIgnoreCase(o2.getShape().trim()); });
        critMap.put("TYPE_ASC", (o1,o2)->{return o1.getType().trim().compareToIgnoreCase(o2.getType().trim()); });
        critMap.put("TYPE_DES", (o2,o1)->{return o1.getType().trim().compareToIgnoreCase(o2.getType().trim()); });
        critMap.put("POINTS_ASC", Comparator.comparingDouble(Medicine::getPoints));
        critMap.put("POINTS_DES", Comparator.comparingDouble(Medicine::getPoints).reversed());
        critMap.put("RATING_ASC", Comparator.comparingDouble(Medicine::getRating));
        critMap.put("RATING_DES", Comparator.comparingDouble(Medicine::getRating).reversed());
        Comparator<Medicine> comp = critMap.getOrDefault(medicineSearchDTO.getSort().toUpperCase().trim(), critMap.values().iterator().next());

        return medicines.stream()
                .filter(m -> medicineSearchDTO.getName().isEmpty() || m.getName().toLowerCase().contains(medicineSearchDTO.getName().toLowerCase()))
                .filter(m -> medicineSearchDTO.getStructure().isEmpty() || m.getSpecification().getStructure().toLowerCase().contains(medicineSearchDTO.getStructure().toLowerCase()))
                .filter(m -> medicineSearchDTO.getManufacturer().isEmpty() || m.getManufacturer().toLowerCase().contains(medicineSearchDTO.getManufacturer().toLowerCase()))
                .filter(m -> medicineSearchDTO.getShape().isEmpty() || m.getShape().toLowerCase().contains(medicineSearchDTO.getShape().toLowerCase()))
                .filter(m -> medicineSearchDTO.getType().isEmpty() || m.getType().toLowerCase().contains(medicineSearchDTO.getType().toLowerCase()))
                .filter(m -> medicineSearchDTO.getStructure().isEmpty() || m.getName().toLowerCase().contains(medicineSearchDTO.getStructure().toLowerCase()))
                .filter(m -> medicineSearchDTO.getPerscription().equalsIgnoreCase("ALL") || m.getPerscription().toString().equalsIgnoreCase(medicineSearchDTO.getPerscription()))
                .filter(m -> m.getRating() >= medicineSearchDTO.getMinRating() && m.getRating() <= medicineSearchDTO.getMaxRating())
                .filter(m -> m.getPoints() >= medicineSearchDTO.getMinPoints() && m.getPoints() <= medicineSearchDTO.getMaxPoints())
                .sorted(comp).collect(Collectors.toList());
    }

    @Override
    public List<PriceInPharmaciesDTO> getPricesOfMedicine(Medicine medicine) {
        ArrayList<PriceInPharmaciesDTO> prices = new ArrayList<PriceInPharmaciesDTO>();
        ArrayList<Pharmacy> allPharmacies = (ArrayList<Pharmacy>) this.pharmacyService.findAll();

        for(Pharmacy ph : allPharmacies){
            for(Iterator<MedicineInPharmacy> i = ph.getMedicines().iterator(); i.hasNext(); ){
                MedicineInPharmacy m = i.next();
                if(m.getMedicine().getCode().equals(medicine.getCode())){
                    prices.add(new PriceInPharmaciesDTO(m.getCurrentPrice().getPrice(), m.getPharmacy().getName()));
                    break;
                }
            }
        }

        return prices;
    }
}
