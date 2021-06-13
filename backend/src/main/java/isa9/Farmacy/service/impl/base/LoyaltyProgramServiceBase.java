package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.LoyaltyProgram;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.service.LoyaltyProgramService;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class LoyaltyProgramServiceBase implements LoyaltyProgramService {

    //return code 1 - category with name already exists
    //return code 2 - category with same limit exists
    //return code 0 - everything is ok
    @Override
    public Integer saveNewUserCategory(LoyaltyProgram loyaltyProgram) {

        for(LoyaltyProgram lp : this.findAll()){
            if(lp.getName().equals(loyaltyProgram.getName())) return 1;
            if(lp.getRequiredPoints() == loyaltyProgram.getRequiredPoints()) return 2;
        }

        this.save(loyaltyProgram);

        return 0;
    }

    @Override
    public Integer updateUserCategory(LoyaltyProgram loyaltyProgram) {

        LoyaltyProgram toBeUpdated = this.findOne(loyaltyProgram.getId());

        toBeUpdated.setName(loyaltyProgram.getName());
        toBeUpdated.setDiscount(loyaltyProgram.getDiscount());
        toBeUpdated.setRequiredPoints(loyaltyProgram.getRequiredPoints());

        List<LoyaltyProgram> otherCategories = this.findAll();
        for(LoyaltyProgram lp : otherCategories){
            if(lp.getId() == toBeUpdated.getId()){
                otherCategories.remove(lp);
                break;
            }
        }

        for(LoyaltyProgram lp : otherCategories){
            if(lp.getName().equals(toBeUpdated.getName())) return 1;
            if(lp.getRequiredPoints() == toBeUpdated.getRequiredPoints()) return 2;
        }


        this.save(toBeUpdated);

        return 0;
    }

    @Override
    public Integer getExaminationPointsReward(){
        int pointsReward = 0;
        String sep = System.getProperty("file.separator");

        File f = new File("src"+sep+"main"+sep+"resources"+sep+"ExaminationPoints.txt");
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try(BufferedReader reader = new BufferedReader(new FileReader(new File("src"+sep+"main"+sep+"resources"+sep+"ExaminationPoints.txt")))){
            String line = reader.readLine();

            pointsReward = Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException ef){
            ef.printStackTrace();
        }

        return pointsReward;
    }

    @Override
    public Integer setExaminationPointsReward(int points) {
        String sep = System.getProperty("file.separator");
        try(PrintWriter writer = new PrintWriter(new FileWriter(new File("src"+sep+"main"+sep+"resources"+sep+"ExaminationPoints.txt"), false))){
            writer.println(points);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public LoyaltyProgram getCategoryOfUser(Patient patient) {
        Comparator<LoyaltyProgram> comparator = Comparator.comparingInt(LoyaltyProgram::getRequiredPoints);

        List<LoyaltyProgram> sortedCategories = this.findAll().stream().sorted(comparator).collect(Collectors.toList());

        LoyaltyProgram categoryOfUser = null;

        for(LoyaltyProgram lp : sortedCategories){
            if(lp.getRequiredPoints() > patient.getPoints()) break;

            categoryOfUser = lp;
        }

        return categoryOfUser;
    }
}
