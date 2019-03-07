/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.cinema.model.*;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cristian
 */

@RestController
public class CinemaAPIController {

	@Autowired
	CinemaServices cinemaServices;

	@GetMapping("/cinemas")
	public ResponseEntity<?> getAllCinemas(){
		try {
			return new ResponseEntity<>(cinemaServices.getAllCinemas(), HttpStatus.ACCEPTED);
		} catch (Exception ex) {
			Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("Error, no cinemas were found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/cinemas/{name}")
	public ResponseEntity<?> getCinemaByName(@PathVariable String name) {
		try {
			return new ResponseEntity<>(cinemaServices.getCinemaByName(name), HttpStatus.ACCEPTED);
		} catch (CinemaException ex) {
			Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("Error, the cinema doesn't exist", HttpStatus.NOT_FOUND);
		} catch (CinemaPersistenceException ex) {
			Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("Error, the persistance of cinema has been violated", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/cinemas/{name}/{date}")
	public ResponseEntity<?> getFunctionsbyCinemaAndDate(@PathVariable String name, @PathVariable String date) {
		try {
			return new ResponseEntity<>(cinemaServices.getFunctionsbyCinemaAndDate(name, date), HttpStatus.ACCEPTED);
		} catch (Exception ex) {
			Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("Error, the cinema does not exist", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/cinemas/{name}/{date}/{moviename}")
	public ResponseEntity<?> getCinemaByCinemaAndDate(@PathVariable String name, @PathVariable String date,
			@PathVariable String moviename) {
		try {
			return new ResponseEntity<>(cinemaServices.getCinemaFunctionbyCinemaDateAndMovie(name, date, moviename),
					HttpStatus.ACCEPTED);
		} catch (Exception ex) {
			Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("Error, no cinema functions were found", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> postSaveCinema(@RequestBody Cinema cinema) {
		try {
			cinemaServices.addNewCinema(cinema);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception ex) {
			Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("Error, the cinema could not be save", HttpStatus.NOT_FOUND);
		}
	}

	
	@RequestMapping(value = "/cinemas/{name}", method = RequestMethod.PUT)	
	public ResponseEntity<?> setCinemaFuction(@PathVariable String name , @RequestBody CinemaFunction cinemaFunction ) {
		try {
			System.out.println("Entremos");
			cinemaServices.setCinemaFuction(name,cinemaFunction);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception ex) {
			Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("Error, the cinema could not be save", HttpStatus.NOT_FOUND);
		}
	}
	
	

}
