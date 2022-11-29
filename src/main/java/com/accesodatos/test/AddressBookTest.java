package com.accesodatos.test;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.accesodatos.daos.AddressBookImpl;
import com.accesodatos.interfaces.AddressBookDAO;
import com.accesodatos.models.Address;

public class AddressBookTest {
		
	// CONSTANTS
	private final static String INVALID_ID = "\033[1;31mID INVALIDO !!\033[0m";
	private final static String EXCEEDED_ID = "\033[1;31mID excede del numero de registros !!\033[0m";
	private final static String NOT_EXIST = "\033[1;31mEl registro no existe\033[0m";
	private final static String CONFIRM_DELETE = "\033[1;31m¿Está seguro de borrar el registro seleccionado?\033[0m";
	private final static int MAX_LENGTH_NAME = 25;
	private final static int MAX_LENGTH_PHONE = 15;
	private final static int MAX_LENGTH_EMAIL = 30;
		
	// INSTANCES
	static Scanner scanner = new Scanner(System.in);
	static AddressBookDAO bookDAO = new AddressBookImpl(); // for calling AddressBookImpl's methods
		
	// PRIVATE METHODS
	
	// The padRight methods inserts the number of spaces introduced in the second parameter of the function
	// it is used for print the tables
	private static String padRight(String s, int n) {
		     return String.format("%-" + n + "s", s);  
	}
		
	private static String padRightLong(Long longNumber, int n) {
		   return String.format("%-" + n + "s", longNumber);  
	}
	
	private static String padRightInt(int s, int n) {
		   return String.format("%-" + n + "s", s);  
	}
			
	// this method allows to press enter then continue
	private static void pressEnterToContinue() {
		String continueEnter = "";
	    System.out.println("Presiona Enter para continuar...");
	    try { continueEnter = scanner.nextLine(); } catch(Exception e) {}
	}
		
	// this method show the address selected information
	private static Address showSingleAddress(Long idForAddress) {
		Address address = bookDAO.getAddress(idForAddress);
		System.out.println(asciiTable[0] + asciiTable[5].repeat(41) + asciiTable[1]);
		System.out.println("│ " + padRight("ID: " + address.getId(), 40) + "│");
		System.out.println("│ " + padRight("Nombre: " + address.getName(), 40) + "│");
		System.out.println("│ " + padRight("Tfno: " + address.getPhone(), 40) + "│");
		System.out.println("│ " + padRight("Email: " + address.getEmail(), 40) + "│");
		System.out.println("│ " + padRight("Edad: " + address.getAge(), 40) + "│");
		System.out.println(asciiTable[2] + asciiTable[5].repeat(41) + asciiTable[3]);
		return address;
	}
	
	// this method prints the number of records in the table 
	private static void showRecordNumber() {
		System.out.println(bookDAO.getNumAddress());
	}
		
	// this method prints the addressbook table with all the data
	private static void showAddressBook() {
		Long records = bookDAO.getNumAddress();
		String asciiTable[] = {"┌", "┐", "└", "┘", "│", "─", "═", "┴", "┬", "╞", "╪", "╡", "╤", "╧", "╟", "╢", "╥", "╨", "╫", "╬", "╪"};
		String field[] = {"Id", "Nombre", "Email", "Telefono", "Edad"};
		StringBuilder valueForId = new StringBuilder();
		List<Address> address = bookDAO.getAllAddress();
		
		for (int i = 0; i < address.size(); i++) {
			Address id = address.get(i);
			valueForId.append(asciiTable[4] + padRightLong(id.getId() == 0 ? id.getId() + 1 : id.getId(), 4) + asciiTable[4] + padRight(id.getName(), 27) + asciiTable[4] + padRight(id.getEmail(), 32) + asciiTable[4] + padRight(id.getPhone(), 17) + asciiTable[4] + padRightInt(id.getAge(), 7) + asciiTable[4]).append("\n");	
		}
        String tableOfContacts = asciiTable[0] + asciiTable[5].repeat(4) + asciiTable[8] + asciiTable[5].repeat(27) + asciiTable[8] + asciiTable[5].repeat(32) + asciiTable[8] + asciiTable[5].repeat(17) + asciiTable[8] + asciiTable[5].repeat(7) + asciiTable[1] +
        		"\n│ " + field[0] + " │" + field[1] + " ".repeat(21) + asciiTable[4] + field[2] + " ".repeat(27) + asciiTable[4] + field[3] + " ".repeat(9) + asciiTable[4] + field[4] + " ".repeat(3) + asciiTable[4] +
        		"\n" + asciiTable[9] + asciiTable[6].repeat(4) + asciiTable[10] + asciiTable[6].repeat(27) + asciiTable[10] + asciiTable[6].repeat(32) + asciiTable[10] + asciiTable[6].repeat(17) + asciiTable[10] + asciiTable[6].repeat(7) + asciiTable[11] +
                "\n" + valueForId +
                 asciiTable[9] + asciiTable[6].repeat(4) + asciiTable[13] + asciiTable[6].repeat(27) + asciiTable[13] + asciiTable[6].repeat(32) + asciiTable[13] + asciiTable[6].repeat(17) + asciiTable[13] + asciiTable[6].repeat(7) + asciiTable[11] + 
                 "\r" + asciiTable[4] + "Nº de registros : " + padRightLong(records, 73) + asciiTable[4] +
                 "\r" + asciiTable[2] + asciiTable[5].repeat(4) + asciiTable[5] + asciiTable[5].repeat(27) + asciiTable[5] + asciiTable[5].repeat(32) + asciiTable[5] + asciiTable[5].repeat(17) + asciiTable[5] + asciiTable[5].repeat(7) + asciiTable[3];
        System.out.println(tableOfContacts);
        pressEnterToContinue();
	 }
		
	static String asciiTable[] = {"┌", "┐", "└", "┘", "│", "─"};
		
	static String option[] = {
			"1. Crear tabla en la DB",
			"2. Añadir nuevo registro",
			"3. Modificar un registro",
			"4. Eliminar registro",
			"5. Buscar registro",
			"6. Listar agenda",
			"7. Eliminar tabla de la DB",
			"8. Terminar programa"
	};
		
		public static void main(String[] args) {
			int selectOption;
			boolean exit = false;

			while (!exit) {
				// here the menu is printed with the different options
				System.out.println(
						asciiTable[0] + asciiTable[5] + " Opciones " + asciiTable[5].repeat(19) + asciiTable[1]);
				for (int i = 0; i < option.length; i++) {
					System.out.println("│ " + padRight(option[i], 29) + "│");
				}
				System.out.println(asciiTable[2] + asciiTable[5].repeat(30) + asciiTable[3]);

				System.out.print("Opción: ");
				selectOption = scanner.nextInt();
				scanner.nextLine();

				switch (selectOption) {
				case 1:
					createTable();
					break;
				case 2:
					addAddress();
					break;
				case 3:
					updateAddress();
					break;
				case 4:
					deleteAddress();
					break;
				case 5:
					searchAddress();
					break;
				case 6:
					listAddress();
					break;
				case 7:
					deleteTable();
					break;
				case 8:
					System.out.println("Terminando programa... ");
					exit = true;
					break;
				default:
					System.out.println("Opcion no válida");
					break;
				}
			}
		}
		
		// this method search the address and prints its information in a table: DONE
		public static void searchAddress() {
			try {
				Address address;
				System.out.print("Introduzca el Id a buscar : ");
				Long idForAddress = scanner.nextLong();
				scanner.nextLine();

				address = bookDAO.getAddress(idForAddress);
				if (address != null) {
					showSingleAddress(idForAddress);
					pressEnterToContinue();
				} else {
					System.out.println("Registro no encontrado.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// this method inserts an address to the database : DONE
		public static void addAddress() {
			Address address = new Address();

			System.out.print("Introduzca el nombre: ");
			String name = scanner.nextLine();
			
			System.out.print("Introduzca el teléfono: ");
			String phone = scanner.nextLine();
			
			System.out.print("Introduzca el email: ");
			String email = scanner.nextLine();
			
			System.out.print("introduzca la edad: ");
			int age = scanner.nextInt();
			scanner.nextLine();

			address.setName(name.substring(0, MAX_LENGTH_NAME));
			address.setPhone(phone.substring(0, MAX_LENGTH_PHONE));
			address.setEmail(email.substring(0, MAX_LENGTH_EMAIL));
			address.setAge(age);

			if (bookDAO.insertAddress(address)) {
				System.out.println("Address registrado correctamente.");
			} else {
				System.out.println("Address no registrado.");
			}
		}
		 
		 // this method is to update the address to the id requested by the user: DONE
		 public static void updateAddress() {
				Address address;
				System.out.print("Introduzca el Id a editar : ");
				Long idForAddress = scanner.nextLong();
				scanner.nextLine();

				if (idForAddress != 0) {
					address = bookDAO.getAddress(idForAddress);
					try {
						showSingleAddress(idForAddress);

						System.out.print("Introduce el nuevo nombre: ");
						String name = scanner.nextLine();
						if (name == "") {
							name = address.getName();
						}

						System.out.print("Introduce el nuevo email: ");
						String email = scanner.nextLine();
						if (email == "") {
							email = address.getEmail();
						}

						System.out.print("Introduce el nuevo teléfono: ");
						String phone = scanner.nextLine();
						if (phone == "") {
							phone = address.getPhone();
						} 

						System.out.print("Introduce el nuevo edad: ");
						String age = scanner.nextLine();
						int age2;
						try {
							age2 = Integer.valueOf(age);
						} catch (NumberFormatException e) {
							age2 = address.getAge();
						}

						address = new Address(name, phone, email, age2);
						bookDAO.updateAddress(address, idForAddress);
					} catch (Exception e) {
						System.out.println(NOT_EXIST + " ó " + EXCEEDED_ID);
					}
				} else {
					System.out.println(INVALID_ID);
				}
			}
		 
			// this method deletes the address selected by the user : DONE
			public static void deleteAddress() {
				
				String confirm = "";
				System.out.print("Introduzca el Id a borrar : ");
				Long idForAddress = scanner.nextLong();
				scanner.nextLine();

				Address address = bookDAO.getAddress(idForAddress);
				if (address != null) {
					showSingleAddress(idForAddress);
					
					// here ask if you are sure to delete the address
					System.out.println(CONFIRM_DELETE + " ID : " + idForAddress);
					System.out.println("presione s/n");
					confirm = scanner.nextLine();
					
					if (confirm.equals("s")) {
						bookDAO.deleteAddress(idForAddress);
					} else {
						return;
					}
				} else {
					System.out.println(NOT_EXIST);
				}
			}
		 
			// this method creates the table "address" in the "addressbook" database: DONE
			public static void createTable() {
				bookDAO.createTableAddress();
			}

			// this method prints the address list using a private method called
			// showAddressBook() : DONE
			public static void listAddress() {
				showAddressBook();
			}

			public static void getNumRecords() {
				showRecordNumber();
			}

			// this method deletes the table from the database: DONE
			public static void deleteTable() {
				bookDAO.dropTableAddress();
			}
}
