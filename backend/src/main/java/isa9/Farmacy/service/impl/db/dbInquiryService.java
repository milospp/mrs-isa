package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.InquiryMedicine;
import isa9.Farmacy.repository.InquiryMedRepository;
import isa9.Farmacy.service.InquiryService;
import isa9.Farmacy.service.impl.base.InquiryServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Component
@Primary
@Service
public class dbInquiryService extends InquiryServiceBase implements InquiryService {

    private final InquiryMedRepository inquiryMedRepository;

    @Autowired
    public dbInquiryService(InquiryMedRepository inquiryMedRepository) {this.inquiryMedRepository = inquiryMedRepository;}

    @Override
    public List<InquiryMedicine> findAll() {
        return this.inquiryMedRepository.findAll();
    }

    @Override
    public InquiryMedicine findOne(Long id) {
        return this.inquiryMedRepository.findById(id).orElse(null);
    }

    @Override
    public InquiryMedicine save(InquiryMedicine entity) {
        return this.inquiryMedRepository.save(entity);
    }

    @Override
    public List<InquiryMedicine> getAllForPharmacy(Long pharmacyId) {
        List<InquiryMedicine> povratna = new ArrayList<>();
        for (InquiryMedicine im : this.inquiryMedRepository.findAll())
            if (im.getPharmacyId().equals( pharmacyId ))
                povratna.add(im);
        return povratna;
    }
}
