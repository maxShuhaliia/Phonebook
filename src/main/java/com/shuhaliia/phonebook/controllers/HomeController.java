package com.shuhaliia.phonebook.controllers;

import java.security.Principal;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.shuhaliia.phonebook.data.Contact;
import com.shuhaliia.phonebook.data.UserErrorRepositoryImpl;
import com.shuhaliia.phonebook.data.UserInfo;

@Controller
@RequestMapping(value="/")
public class HomeController {
	
	public String searchColumn = "firstname";
	@Autowired
	private UserErrorRepositoryImpl userErrorRepositoryImpl;
	 @Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 @Autowired
	private  JdbcTemplate jdbcTemplate;
	 
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showPhonebookPage(Model model, Principal principal, HttpSession session){
		List<Contact> contacts = getContacts(principal, session);	
		model.addAttribute("contacts", contacts);
		model.addAttribute("arrow", session.getAttribute("arrow"));
		model.addAttribute("column", session.getAttribute("column"));
		model.addAttribute("searchBy", session.getAttribute("searchBy"));
		return "phonebookPage";
		}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginPage(){
		return "login";
		}
	
	@RequestMapping(value="/register")
	public String showRegisterPage(Model model){
		UserInfo userInfo = new UserInfo();
		if(model.containsAttribute("user")){
			userInfo = userErrorRepositoryImpl.getUser();
			model.addAttribute("user" ,userInfo);
		}
		model.addAttribute("user", userInfo);
		return "/register";
	}
	
	@RequestMapping(value="/addContact")
	public String addContact(Contact contact, Principal principal){
		String insertContact = "INSERT INTO contacts( id_contact, login, firstname, lastname, middlename, mobilephone,"+
								" homephone, address, email) "
								+ "VALUES(:one, :two, :three, :four, :five, :six, :seven, :eight, :nine)";		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("one", null);
		parameters.put("two", principal.getName());
		parameters.put("three", contact.getFirstName());
		parameters.put("four", contact.getLastName());
		parameters.put("five", contact.getMiddleName());
		parameters.put("six", contact.getMobilePhone());
		parameters.put("seven", contact.getHomePhone());
		parameters.put("eight", contact.getAddress());
		parameters.put("nine", contact.getEmail());
		insertContact(insertContact, parameters);
		return "redirect:/";
}
	
	@RequestMapping(value="/removeContact/{idContact}")
	public String removeContact(@PathVariable String idContact){
		String removeContact = "DELETE FROM contacts where id_contact=?";
		jdbcTemplate.update(removeContact, idContact);
		return "redirect:/";
	}
	
	@RequestMapping(value="/editContact/{id}")
	public String editContact(@PathVariable int id, Contact contact){
		String updateRow = "UPDATE contacts SET "
				+ "firstname = :firstname, "
				+ "lastname = :lastname, "
				+ "middlename = :middlename, "
				+ "mobilephone = mobilephone, "
				+ "homephone = :homephone, "
				+ "address = :address, "
				+ "email = :email "
				+ "WHERE id_contact = :id";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("firstname", contact.getFirstName());
		parameters.put("lastname", contact.getLastName());
		parameters.put("middlename", contact.getMiddleName());
		parameters.put("mobilephone", contact.getMobilePhone());
		parameters.put("homephone", contact.getHomePhone());
		parameters.put("address", contact.getAddress());
		parameters.put("email", contact.getEmail());
		parameters.put("id", id);
		insertContact(updateRow, parameters);
		return "redirect:/";
	}
	
	public void insertContact(String insertContact, Map<String, Object> parameters){
		int row = namedParameterJdbcTemplate.update(insertContact, parameters);
	}
	
	public List<Contact> getContacts(Principal principal, HttpSession session){
		String sql = "SELECT * FROM contacts WHERE login='";
		String userLogin = principal.getName();
		String select = sql+userLogin+"'";
		List<Contact> contacts = executeQueryFor(select, session);
		return contacts;
	}
	
	@RequestMapping(value="/setSortBy")
	@ResponseBody
	public String setSortBy(@RequestParam("arrow") String arrow, @RequestParam("column") String column, 
												HttpSession session, Principal principal){
		session.setAttribute("arrow", arrow);
		session.setAttribute("column", column);
		List<Contact> contacts = getContacts(principal, session);
		Gson gson = new Gson();
		String contactsJsonStr = gson.toJson(contacts);
		return contactsJsonStr;
}
	
	public List<Contact> sortBy(String arrow, String column, List<Contact> contacts){
		if(column.equals("firstname")){
			//firstname
			for(int i = 0; i < contacts.size(); i++){
				for(int j = 0; j < contacts.size()-i-1; j++){		
					if( (contacts.get(j).getFirstName()).compareTo(contacts.get(j+1).getFirstName()) > 0 ){
						Contact contact = contacts.get(j);
						contacts.set(j, contacts.get(j+1) );
						contacts.set(j+1, contact );
						}
				}
			}//end for
		}else if(column.equals("lastname")){	
			//lastname
			for(int i = 0; i < contacts.size(); i++){
				for(int j = 0; j < contacts.size()-i-1; j++){
					if( (contacts.get(j).getLastName()).compareTo(contacts.get(j+1).getLastName()) > 1 ){
						Contact contact = contacts.get(j);
						contacts.set(j, contacts.get(j+1) );
						contacts.set(j+1, contact );
					}
				}
			}//end for
		}else if(column.equals("middlename")){	
			//middlename
					for(int i = 0; i < contacts.size(); i++){
						for(int j = 0; j < contacts.size()-i-1; j++){
							if( (contacts.get(j).getMiddleName()).compareTo(contacts.get(j+1).getMiddleName()) > 1 ){
								Contact contact = contacts.get(j);
								contacts.set(j, contacts.get(j+1) );
								contacts.set(j+1, contact );
							}
						}
			}//end for
		}else if(column.equals("mobilephone")){		
				//mobilePhone
					for(int i = 0; i < contacts.size(); i++){
						for(int j = 0; j < contacts.size()-i-1; j++){
							if( (contacts.get(j).getMiddleName()).compareTo(contacts.get(j+1).getMiddleName()) > 1 ){
								Contact contact = contacts.get(j);
								contacts.set(j, contacts.get(j+1) );
								contacts.set(j+1, contact );
							}
						}
			}//end for	
		}			
		if(arrow.equals("decrease")){
			List<Contact> newContacts = new ArrayList<Contact>();	
			for(int i = contacts.size()-1; i >= 0; i-- ){
				newContacts.add( contacts.get(i) );
			}
			contacts = newContacts;
		}
		return contacts;
	}
	
	@ResponseBody
	@RequestMapping(value="/setSerachBy/{column}")
	public String setSerachBy(@PathVariable String column, HttpSession session){
		searchColumn = column;
		session.setAttribute("searchBy", searchColumn);
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/findAllContacts/{text}")
	public String findAllContacts(@PathVariable String text, Principal principal, HttpSession session){
		String sqlSearch = "SELECT * FROM contacts WHERE login='"+principal.getName()+"' and "+searchColumn+" LIKE '"+text+"%'";
		List<Contact> contacts = executeQueryFor( sqlSearch, session );
		Gson gson = new Gson();
		String result = gson.toJson(contacts);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getAllContacts")
	public String getAllContacts( Principal principal, HttpSession session ){
		List<Contact> contacts = getContacts( principal, session );
		Gson gson = new Gson();
		String result = gson.toJson(contacts);
		return result;
	}

	public List<Contact> executeQueryFor(String sql, HttpSession session){
		List<Contact> contacts = new ArrayList<Contact>();	
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) {
			Contact contact = new Contact();			
			contact.setFirstName((String)(row.get("firstname")));
			contact.setLastName((String)(row.get("lastname")));
			contact.setMiddleName((String)(row.get("middlename")));
			contact.setMobilePhone((String)(row.get("mobilephone")));
			contact.setHomePhone((String)(row.get("homephone")));
			contact.setAddress((String)(row.get("address")));
			contact.setEmail((String)(row.get("email")));
			contact.setContactId((int)(row.get("id_contact")));		
			contacts.add(contact);
		}	
		if(session.getAttribute("arrow")!=null){
			contacts = sortBy( session.getAttribute("arrow").toString(), session.getAttribute("column").toString(), contacts);
		}
		return contacts;
	}// end executeQueryFor
}