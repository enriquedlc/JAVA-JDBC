package com.accesodatos.interfaces;

import java.util.List;

import com.accesodatos.models.Address;

public interface AddressBookDAO {
	
	public Address getAddress(Long addressId);
	public boolean insertAddress(Address address);
	public boolean updateAddress(Address address, Long addressId);
	public boolean deleteAddress(Long addressId);
	public List<Address>getAllAddress();
	public Long getNumAddress();
	public boolean createTableAddress();
	public boolean dropTableAddress();
}
