package isa9.Farmacy.support;

import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.Supplier;
import isa9.Farmacy.model.dto.PharmacyDTO;
import isa9.Farmacy.model.dto.RolesDTO;
import isa9.Farmacy.model.dto.SupplierDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierToSupplierDTO implements Converter<Supplier, SupplierDTO> {
    @Override
    public SupplierDTO convert(Supplier supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();

        supplierDTO.setAddress(supplier.getAddress());
        supplierDTO.setEmail(supplier.getEmail());
        supplierDTO.setId(supplier.getId());
        supplierDTO.setName(supplier.getName());
        supplierDTO.setSurname(supplier.getSurname());
        supplierDTO.setRole(RolesDTO.SUPPLIER);
        supplierDTO.setPhoneNumber(supplier.getPhoneNumber());

        return supplierDTO;
    }

    public List<SupplierDTO> convert(List<Supplier> suppliers) {
        List<SupplierDTO> dto = new ArrayList<>();
        for (Supplier s : suppliers){
            dto.add(convert(s));
        }
        return dto;
    }
}
