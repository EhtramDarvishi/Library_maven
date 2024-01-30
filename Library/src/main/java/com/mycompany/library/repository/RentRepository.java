/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.repository;

import com.mycompany.library.Entity.Rent;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//public interface RentRepository extends JpaRepository<Rent, Long> {
//
//}
public interface RentRepository extends JpaRepository<Rent, Long> {

    int countByMemberId(Long memberId);

    public Optional<Rent> findByMemberIdAndBookId(Long memberId, Long bookId);

    public int countByBookId(Long bookId);

    @Query("SELECT r FROM Rent r WHERE r.borrowDate < :twoWeeksAgo AND r.returnDate IS NULL")
    public List<Rent> findOverdueRents(LocalDate twoWeeksAgo);

}
