package isa9.Farmacy.support;

import isa9.Farmacy.model.MedicineOrder;
import isa9.Farmacy.model.dto.MedicineOrderDTO;
import isa9.Farmacy.model.dto.MedicineQuantityDTO;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class MedOrderToMedOrderDTO implements Converter<MedicineOrder, MedicineOrderDTO> {

    @Override
    public MedicineOrderDTO convert(MedicineOrder medicineOrder) {
        MedicineOrderDTO povratna = new MedicineOrderDTO();
        povratna.setStartDate(medicineOrder.getStartDate());
        povratna.setEndDate(medicineOrder.getEndDate());

        MedicineQuantityToMedicineQuantityDTO medQuantity = new MedicineQuantityToMedicineQuantityDTO();
        List<MedicineQuantityDTO> mqPomocno = new ArrayList<>();
        if (medicineOrder.getAllMedicines().size() != 0) mqPomocno = medQuantity.convert(medicineOrder.getAllMedicines());
        povratna.setAllMedicines(mqPomocno);

        PAdminToPAdminDTO pAdmin = new PAdminToPAdminDTO();
        povratna.setAuthor(pAdmin.convert(medicineOrder.getAuthor()));

        PharmacyToPharmacyDTO apoteka = new PharmacyToPharmacyDTO();
        povratna.setPharmacy(apoteka.convert(medicineOrder.getPharmacy()));

        OfferToOfferDTO ponuda = new OfferToOfferDTO();
        povratna.setChosenOffer((medicineOrder.getChosenOffer() == null ? null : ponuda.convert(medicineOrder.getChosenOffer())));

        povratna.setAllOffer((medicineOrder.getAllOffer().size() == 0 ? new ArrayList<>() : ponuda.convert(medicineOrder.getAllOffer())));

        return povratna;
    }


    public List<MedicineOrderDTO> convert(List<MedicineOrder> medicineOrders) {
        List<MedicineOrderDTO> povratna = new ArrayList<>();
        for (MedicineOrder medOrder : medicineOrders) povratna.add(convert(medOrder));
        return povratna;
    }
}
