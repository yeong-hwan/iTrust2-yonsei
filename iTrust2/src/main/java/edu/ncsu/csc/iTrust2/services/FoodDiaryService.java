package edu.ncsu.csc.iTrust2.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.forms.FoodDiaryForm;
import edu.ncsu.csc.iTrust2.models.AppointmentRequest;
import edu.ncsu.csc.iTrust2.models.FoodDiary;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.repositories.FoodDiaryRepository;

@Component
@Transactional
public class FoodDiaryService extends Service {

    @Autowired
    private FoodDiaryRepository repository;

    @Override
    protected JpaRepository getRepository() {
        return repository;
    }

    public List<FoodDiary> findByIdContains(final Long id) {
        return repository.findAllByIdContaining(id);
    }

    public List<FoodDiary> findByDateContains(final Date date) {
        return repository.findAllByDateContaining(date);
    }

    public List<FoodDiary> findByMealTypeContains(final String mealType) {
        return repository.findAllByMealTypeContaining(mealType);

    public FoodDiary addEntry(FoodDiary entry) {
        return repository.save(entry);
    }

    /**
     * 5~11 더하는 건데,, 작동할지는 모르겠습니다,, 만들고 수정하는걸로,,
     */
    // public FoodDiary calculateDailyTotal(Date date) {
    // List<FoodDiary> entries = repository.findAllByDate(date);
    // FoodDiary dailyTotal = new FoodDiary();
    // dailyTotal.setDate(date);
    // dailyTotal.setServingNumber(entries.stream().mapToLong(FoodDiaryEntry::getServings).sum());
    // dailyTotal.setCaloriesPerServing(
    // entries.stream().mapToLong(e -> e.getCaloriesPerServing *
    // e.getServingNumber()).sum());
    // dailyTotal.setFatPerServing(entries.stream().mapToLong(e ->
    // e.getFatPerServing() * e.getServingNumber()).sum());
    // dailyTotal.setSodiumPerServing(
    // entries.stream().mapToLong(e -> e.getSodiumPerServing *
    // e.getServingNumber()).sum());
    // dailyTotal
    // .setCarbsPerServing(entries.stream().mapToLong(e -> e.getCarbsPerServing *
    // e.getServingNumber()).sum());
    // dailyTotal.setSugarsPerServing(
    // entries.stream().mapToLong(e -> e.getSugarsPerServing *
    // e.getServingNumber()).sum());
    // dailyTotal
    // .setFiberPerServing(entries.stream().mapToLong(e -> e.getFiberPerServing *
    // e.getServingNumber()).sum());
    // dailyTotal.setProteinPerServing(
    // entries.stream().mapToLong(e -> e.getProteinPerServing *
    // e.getServingNumber()).sum());

    // return dailyTotal;
    // }
}
