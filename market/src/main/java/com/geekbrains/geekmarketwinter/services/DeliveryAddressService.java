package com.geekbrains.geekmarketwinter.services;

import com.geekbrains.geekmarketwinter.entities.DeliveryAddress;
import com.geekbrains.geekmarketwinter.entities.User;
import com.geekbrains.geekmarketwinter.repositories.DeliveryAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryAddressService {
	@Autowired
	private DeliveryAddressRepository deliveryAddressRepository;
	
	public List<DeliveryAddress> getUserAddresses(Long userId) {
		return deliveryAddressRepository.findAllByUserId(userId);
//		ArrayList<DeliveryAddress> listOfAddresses = new ArrayList<>();
//		listOfAddresses.add(new DeliveryAddress());
//		return listOfAddresses;
	}
}
