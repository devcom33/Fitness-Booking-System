package org.heymouad.bookingmanagementsystem.repositories;

import org.heymouad.bookingmanagementsystem.dtos.admin.CategoriesStatsDTO;
import org.heymouad.bookingmanagementsystem.entities.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, UUID> {

    @Query(
        """
            SELECT new org.heymouad.bookingmanagementsystem.dtos.admin.CategoriesStatsDTO(c.name, COUNT(f))
                    FROM Category as c LEFT JOIN c.fitnessClasses as f
                            GROUP BY c.name
        """
    )
    List<CategoriesStatsDTO> countFitnessPerCategories();
}
