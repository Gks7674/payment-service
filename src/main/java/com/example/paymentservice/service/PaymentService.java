package com.example.paymentservice.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {

	private static final Logger logger = Logger.getLogger(PaymentService.class.getName());

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final String TOPIC = "payment-topic";

	@Transactional
	public Payment createPayment(Payment payment) {
		logger.info("Creating payment: " + payment);
		Payment savedPayment = paymentRepository.save(payment);
		kafkaTemplate.send(TOPIC, "Payment Created: " + savedPayment.getId());
		return savedPayment;
	}

	public List<Payment> getAllPayments() {
		return paymentRepository.findAll();
	}

	public Optional<Payment> getPaymentById(Long id) {
		return paymentRepository.findById(id);
	}

	public Payment updatePaymentStatus(Long id, String status) {
		Optional<Payment> optionalPayment = paymentRepository.findById(id);
		if (optionalPayment.isPresent()) {
			Payment payment = optionalPayment.get();
			payment.setStatus(status);
			return paymentRepository.save(payment);
		} else {
			throw new RuntimeException("Payment not found");
		}
	}

	public void deletePayment(Long id) {
		paymentRepository.deleteById(id);
	}

}
