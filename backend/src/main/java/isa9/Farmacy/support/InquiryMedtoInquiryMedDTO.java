package isa9.Farmacy.support;

import isa9.Farmacy.model.InquiryMedicine;
import isa9.Farmacy.model.dto.InquiryMedicineDTO;
import isa9.Farmacy.model.dto.MedicineDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InquiryMedtoInquiryMedDTO implements Converter<InquiryMedicine, InquiryMedicineDTO> {

    @Override
    public InquiryMedicineDTO convert(InquiryMedicine inquiryMedicine) {
        InquiryMedicineDTO povratna = new InquiryMedicineDTO();
        povratna.setId(inquiryMedicine.getId());
        povratna.setInquiryDate(inquiryMedicine.getInquiryDate());
        MedicineToMedicineDTO konverterLekova = new MedicineToMedicineDTO();
        povratna.setMedicine(konverterLekova.convert(inquiryMedicine.getMedicine()));
        DoctorToDoctorDTO konverterDoktor = new DoctorToDoctorDTO();
        povratna.setDoctor(konverterDoktor.convert(inquiryMedicine.getDoctor()));
        return povratna;
    }

    public List<InquiryMedicineDTO> convert(List<InquiryMedicine> inquiryMedicines) {
        List<InquiryMedicineDTO> povratna = new ArrayList<>();
        for (InquiryMedicine im : inquiryMedicines) povratna.add(convert(im));
        return povratna;
    }
}
