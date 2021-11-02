package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user=:user"),
        @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m WHERE m.id=:id AND m.user=:user"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user=:user ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN_HALF_OPEN, query = "SELECT m FROM Meal m WHERE m.user=:user" +
                " AND m.dateTime >=:startDateTime AND m.dateTime <: endDateTime ORDER BY m.dateTime DESC"),
})
@Entity
@Table(name = "meals")
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String GET = "Meal.get";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String GET_BETWEEN_HALF_OPEN = "Meal.getBetweenHalfOpen";

    @Column(name = "date_time", nullable = false, unique = true)
    @NotEmpty
    private LocalDateTime dateTime;

    @Column(name = "description")
    private String description;

    @Column(name = "calories", nullable = false)
    @NotEmpty
    @Range(min = 10, max = 10000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
