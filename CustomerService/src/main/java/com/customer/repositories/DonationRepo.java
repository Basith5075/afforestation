package com.customer.repositories;

import com.customer.models.CustomerDonation;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepo extends JpaRepository<CustomerDonation, Integer> {
}
