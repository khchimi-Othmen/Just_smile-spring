package kh.houssem.just_smile.Repositories;

import kh.houssem.just_smile.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientPatientId(Long patientId);

    List<Appointment> findByPatientPatientIdAndDateAndTime(Long patientId, Date date, String time);

    List<Appointment> findByDate(Date date);

    List<Appointment> findByDateOrderByStartTimeAsc(Date date);
}
