package isa9.Farmacy.service;

import isa9.Farmacy.model.LoyaltyProgram;
import isa9.Farmacy.model.Patient;

public interface LoyaltyProgramService extends GenericService<LoyaltyProgram>{

    Integer saveNewUserCategory(LoyaltyProgram loyaltyProgram);
    Integer updateUserCategory(LoyaltyProgram loyaltyProgram);
    Integer getExaminationPointsReward();
    Integer setExaminationPointsReward(int points);
    LoyaltyProgram getCategoryOfUser(Patient patient);

}
