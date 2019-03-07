/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

import edu.eci.arsw.cinema.persistence.CinemaException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author cristian
 */
public class CinemaFunction {
    
    private Movie movie;
    private List<List<AtomicBoolean>> seats=new ArrayList<>();
    private String date;
    
    
    /*private class Silla {
    	boolean tomada;
    	public void set(boolean t) {
    		this.tomada = t;
    	}
    	
    	public boolean get() {
    		return this.tomada;
    	}
    }*/
    
    public CinemaFunction(){}
    
    /*private void spm(int i, int j) {
    	//Silla silla = seats.get(i).get(j);
    	synchronized(silla) {
    		if (silla.get() == false) {
    			System.out.println("Pude cogerla!!!");
    			silla.set(true);
    		}
    	}
    }*/
    
    public CinemaFunction(Movie movie, String date){
        this.movie=movie;
        this.date=date;
        //7x12
        for (int i=0;i<7;i++){
            List<AtomicBoolean> row= new ArrayList<>(Arrays.asList(new AtomicBoolean[12]));
            Collections.fill(row, new AtomicBoolean(true));
            this.seats.add(row);
        }
    }
    
    public void buyTicket(int row,int col) throws CinemaException{
        if (seats.get(row).get(col).equals(true)){
            seats.get(row).set(col, new AtomicBoolean(false));
        }else{
            throw new CinemaException("Seat booked");
        }
    }
    
    public int getEmptySeats(){
        int countEmptySeat = 0;
        for(List<AtomicBoolean> listaBoolean  : seats){
            for(AtomicBoolean emptySeat  : listaBoolean){
                if(emptySeat.get()){
                    countEmptySeat++;
                }
            }            
        }
        return countEmptySeat;
    }
    
    public List<List<AtomicBoolean>> getSeats() {
        return this.seats;
    }
    
    public Movie getMovie() {
        return movie;
    }

    synchronized public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getDate() {
        return date;
    }

    synchronized public void setDate(String date) {
        this.date = date;
    }
    
}
