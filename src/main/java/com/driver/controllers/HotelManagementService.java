package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

public class HotelManagementService {

      HotelManagementRepository hotelManagementRepository=new HotelManagementRepository();

        public String addHotel( Hotel hotel){

            //You need to add an hotel to the database
            //incase the hotelName is null or the hotel Object is null return an empty a FAILURE
            //Incase somebody is trying to add the duplicate hotelName return FAILURE
            //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.
           return hotelManagementRepository.addHotel(hotel);
        }
        public Integer addUser( User user){

            //You need to add a User Object to the database
            //Assume that user will always be a valid user and return the aadharCardNo of the user
             return hotelManagementRepository.addUser(user);
        }
        public String getHotelWithMostFacilities(){

            //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
            //Incase there is a tie return the lexicographically smaller hotelName
            //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)


            return hotelManagementRepository.getHotelWithMostFacilities();
        }
        public int bookARoom( Booking booking){

            //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
            //Have bookingId as a random UUID generated String
            //save the booking Entity and keep the bookingId as a primary key
            //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
            //If there are not enough rooms available in the hotel that we are trying to book return -1
            //in other case return total amount paid
             return hotelManagementRepository.bookARoom(booking);
        }
        public int getBookings(Integer aadharCard)
        {
            //In this function return the bookings done by a person
            //count return
          return hotelManagementRepository.getBookings(aadharCard);
        }
        public Hotel updateFacilities(List<Facility> newFacilities, String hotelName){

            //We are having a new facilites that a hotel is planning to bring.
            //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
            //return the final updated List of facilities and also update that in your hotelDb
            //Note that newFacilities can also have duplicate facilities possible

            return hotelManagementRepository.updateFacilities(newFacilities,hotelName);
        }


    }


