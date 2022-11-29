package com.accesodatos.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.accesodatos.db.DatabaseConnection;
import com.accesodatos.interfaces.AddressBookDAO;
import com.accesodatos.models.Address;

public class AddressBookImpl implements AddressBookDAO {
	
	// SQL Statements
	static final String SQL_FIND_BY_ID = "SELECT * FROM address WHERE (address_id = ?);";
	static final String SQL_INSERT = "INSERT INTO address (address_name, address_phone, address_email, address_age) VALUES ( ?, ?, ?, ? );";
	static final String SQL_DELETE = "DELETE FROM address WHERE (address_id = ?);";
	static final String SQL_UPDATE = "UPDATE address SET address_name = ?, address_phone = ?, address_email = ?, address_age = ? WHERE (address_id = ?);";
	static final String SQL_LIST = "SELECT * FROM address;";
	static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `addressbook`.`address` (\r\n"
			+ "  `address_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
			+ "  `address_name` VARCHAR(25) NOT NULL,\r\n"
			+ "  `address_phone` VARCHAR(15) NOT NULL,\r\n"
			+ "  `address_email` VARCHAR(30) NOT NULL,\r\n"
			+ "  `address_age` INT NOT NULL,\r\n"
			+ "  PRIMARY KEY (`address_id`));";
	static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS address;";	
	static final String SQL_COUNT_RECORDS = "SELECT COUNT(*) AS Quantity FROM address";
	
	// PRIVATE METHODS
	
	/*
	 * The processRow method returns the address with each field
	 */
	private Address processRow(ResultSet resultSet) throws SQLException {
		return new Address(	resultSet.getLong("address_id"), 
							resultSet.getString("address_name"),
							resultSet.getString("address_phone"), 
							resultSet.getString("address_email"),
							resultSet.getInt("address_age"));
	}
	
	/* 
	 * getConnection method returns the instance and connection to the database 
	 */
	private static Connection getConnection() throws SQLException {
		return DatabaseConnection.getInstance().getConnection();	}
	
	
	// CLASS METHODS
	
	/*
	 * GetAddress returns an address receiving the address id which is a long.
	 * The id is seeked by the sql query (SQL_FIND_BY_ID) using the function prepareCall().
	 */
	@Override
	public Address getAddress(Long addressId) {
		Address address = null;
		
		try (PreparedStatement preQuery = getConnection().prepareCall(SQL_FIND_BY_ID);) {

			preQuery.setLong(1, addressId);
			ResultSet resultSet = preQuery.executeQuery();

			/*
			 * The process row it's going to return the data until there is no more
			 */
			if (resultSet.next())
				address = processRow(resultSet);

		} catch (SQLException e) {
			Logger.getLogger(AddressBookImpl.class.getName()).log(Level.SEVERE, "Database connection Failed", e);
		}
		return address;
	}

	/*
	 * This method inserts and address using the sql query "SQL_INSERT" 
	 * with the prepareStatement() function.
	 * Returns if it's was successful or not.
	 */
	@Override
	public boolean insertAddress(Address address) {
		boolean isSuccessfully = false;

		try (PreparedStatement preQuery = getConnection().prepareStatement(SQL_INSERT);) {

			/*
			 * Each field is setted with the preQuery PreparedStatement.
			 * The set method receives the column as first parameter and a second
			 * parameter which is a string, int, long, etc.
			 */
			preQuery.setString(1, address.getName());
			preQuery.setString(2, address.getPhone());
			preQuery.setString(3, address.getEmail());
			preQuery.setInt(4, address.getAge());
			
			if (preQuery.executeUpdate() > 0) isSuccessfully = true;
			
		} catch (SQLException e) {
			Logger.getLogger(AddressBookImpl.class.getName()).log(Level.SEVERE, "Database connection Failed", e);
		}
		return isSuccessfully;
	}

	/*
	 * updateAddress returns if it's was successful or not.
	 * This method updates an address using the sql query "SQL_UPDATE", 
	 * receiving by parameter the address to update for changing the fields 
	 * and the id to seek the specified address.
	 */
	@Override
	public boolean updateAddress(Address address, Long addressId) {
		boolean isSuccessfully = false;

		try (PreparedStatement preQuery = getConnection().prepareStatement(SQL_UPDATE);) {

			/*
			 * Each field is setted with the preQuery PreparedStatement.
			 * The set method receives the column as first parameter and a second
			 * parameter which is a string, int, long, etc.
			 */
			preQuery.setString(1, address.getName());
			preQuery.setString(2, address.getPhone());
			preQuery.setString(3, address.getEmail());
			preQuery.setInt(4, address.getAge());
			preQuery.setLong(5, addressId);
			
			if (preQuery.executeUpdate() > 0) isSuccessfully = true;
			
		} catch (SQLException e) {
			Logger.getLogger(AddressBookDAO.class.getName()).log(Level.SEVERE, "Database connection Failed", e);
		}
		return isSuccessfully;
	}

	/*
	 * deleteAddress returns if it's was successful or not. 
	 * Deletes the address seeked by parameter which is a long.
	 * The address is deleted using the sql query "SQL_DELETE".
	 */
	@Override
	public boolean deleteAddress(Long addressId) {
		boolean isSuccessfully = false;

		try (PreparedStatement preQuery = getConnection().prepareStatement(SQL_DELETE);) {
			
			/*
			 * setLong receives by parameter the first column where are located
			 * the id of each address, in the column the addressId is seeked
			 * an deleted using the sql query "SQL_DELETE".
			 */
			preQuery.setLong(1, addressId);
		
			if (preQuery.executeUpdate() > 0) isSuccessfully = true;
			
		} catch (SQLException e) {
			Logger.getLogger(AddressBookDAO.class.getName()).log(Level.SEVERE, "Database connection Failed", e);
		}
		return isSuccessfully;
	}

	/*
	 * getAllAddress returns an address.
	 * Using "SQL_LIST" all the data in the table is obtained.
	 */
	@Override
	public List<Address> getAllAddress() {
		List<Address> address = new ArrayList<>();

		// we execute the command with resultSet
		try (ResultSet data = getConnection().prepareStatement(SQL_LIST)
											.executeQuery();) {
			
			/*
			 * while there is more rows in the table
			 * each processRow is added to the list of address
			 * then it's showed in the main program.
			 */
			while (data.next()) {
				address.add(processRow(data));
			}

		} catch (Exception e) {
			Logger.getLogger(AddressBookDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return address;
	}

	/*
	 * getNumAddress returns the number (long) of records in the table.
	 * Using the prepareStatement() function, the sql query SQL_COUNT_RECORDS
	 * and executing the query, the number of records is obtained (recordCounter).
	 */
	@Override
	public Long getNumAddress() {
		Long recordCounter = (long) 0;
		try (ResultSet data = getConnection().prepareStatement(SQL_COUNT_RECORDS)
												.executeQuery();) {

			/*
			 * while there is more rows in the table
			 * the recordCounter is increased by 1.
			 */
			while (data.next()) {
				 recordCounter = data.getLong(1);
			}

		} catch (Exception e) {
			Logger.getLogger(AddressBookDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return recordCounter;
	}

	/*
	 * createTableAddress returns if it's was successful or not creating the table.
	 * Using the sql query "SQL_CREATE_TABLE" the table is created.
	 */
	@Override
	public boolean createTableAddress() {
		boolean isSuccessFully = false;
		
		try (PreparedStatement preQuery = getConnection().prepareCall(SQL_CREATE_TABLE);) {

			if (preQuery.executeUpdate() > 0) isSuccessFully = true;
			
		} catch (Exception e) {
			Logger.getLogger(AddressBookDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return isSuccessFully;
	}

	/*
	 * createTableAddress returns if it's was successful or not dropping the table.
	 * Using the sql query "SQL_CREATE_TABLE" the table is dropped.
	 */
	@Override
	public boolean dropTableAddress() {
		boolean isSuccessFully = false;
		
		try (PreparedStatement preQuery = getConnection().prepareCall(SQL_DROP_TABLE);) {
			
			if (preQuery.executeUpdate() > 0) isSuccessFully = true;

		} catch (Exception e) {
			Logger.getLogger(AddressBookDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return isSuccessFully;
	}
}
