package isa9.Farmacy.service;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.PatientLastAppointmentDTO;
import isa9.Farmacy.support.PaginationSortSearchDTO;
import isa9.Farmacy.support.PatientsPagesDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface UserService extends GenericService<User> {

    boolean isAvaibleEmail(String em);
    boolean isAvaibleEmail(String em, Long id);

    User getByEmail(String email);

    Set<Medicine> getPatientAllergies(User patient);
    Set<Pharmacy> getPatientSubscriptions(User patient);
    void PatientUnsubscribe(User patient, Pharmacy pharmacy);

    Set<Penality> getPenalties(User user);
    int countActivePenalties(User user);
    boolean isPatientBlocked(Patient patient);

        PharmacyAdmin findPharmacyAdmin(Long pharmacyId);

    Patient updatePatient(PatientDTO patientDTO);

    //Pharmacy findPharmacistPharmacy(Long id);

    Set<Medicine> getPatientAllergies(Long patientId);
    Medicine addPatientAllergy(Patient patient, Long medicineId);
    Medicine removePatientAllergy(Patient patient, Long medicineId);

    Set<MedReservation> getPatientReservations(Long patientId);

    List<Patient> getAllMyPatientsPaged(PaginationSortSearchDTO pssDTO);
    long getAllMyPatientsTotalCount(PaginationSortSearchDTO pssDTO);
    List<Patient> getPatientsByDoctorIdAndSearchAndSortByDateAsc(PaginationSortSearchDTO pssDTO);

    PatientsPagesDTO getPatientLastAppointmentDTOsSortedSearched(PaginationSortSearchDTO pssDTO);

    Doctor getDoctorById(Long id);
    Doctor getDoctorByIdLocked(Long id);
    Patient getPatientById(Long id);
    Patient getPatientByIdLocked(Long id);

    Doctor updateDoctorRating(Doctor doctor);

    User getLoggedInUser();

    boolean changePassword(Long id, String newPassword);

    Penality addPenalty(Patient patient, String reason);

    List<Doctor> getVisitedDoctors(Patient patient);

    List<Patient> howSucribePharmacy(Long pharmacyId);

    Integer activateUser(String token);
}
