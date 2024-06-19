package com.example.paymentservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private static final Logger logger = Logger.getLogger(PaymentController.class.getName());

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	public Payment createPayment(@RequestBody Payment payment) {
		logger.info("Creating payment: " + payment);
		return paymentService.createPayment(payment);
	}

	@PutMapping("/{id}/status")
	public Payment updatePaymentStatus(@PathVariable Long id, @RequestBody String status) {
		logger.info("Updating payment status for payment ID: " + id);
		return paymentService.updatePaymentStatus(id, status);
	}

	@GetMapping
	public List<Payment> getAllPayments() {
		logger.info("Getting all payments");
		return paymentService.getAllPayments();
	}

	@GetMapping("/{id}")
	public Optional<Payment> getPaymentById(@PathVariable Long id) {
		logger.info("Getting payment by ID: " + id);
		return paymentService.getPaymentById(id);
	}

}
