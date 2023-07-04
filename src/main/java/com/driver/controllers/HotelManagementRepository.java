package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public class HotelManagementRepository {
    Map<String, Hotel> hoteldb=new HashMap<>();
    Map<Integer, User> Userdb=new HashMap<>();
    Map<String, Booking> bookingroomdb=new HashMap<>();

    public String addHotel(Hotel hotel){

        //You need to add an hotel to the database
        //incase the hotelName is null or the hotel Object is null return an empty a FAILURE
        //Incase somebody is trying to add the duplicate hotelName return FAILURE
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.
        if(hotel.getHotelName() == null){
            return "FAILURE";
        }
        if(hoteldb.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hoteldb.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }


    public Integer addUser(User user){

        //You need to add a User Object to the database
        //Assume that user will always be a valid user and return the aadharCardNo of the user
        Userdb.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }


    public String getHotelWithMostFacilities(){

        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)
        int max = 0;
         List<String> hotelname=new ArrayList<>();
        for(String  hotelName : hoteldb.keySet()){
            int facilities=(hoteldb.get(hotelName).getFacilities().size());
            if(facilities>max){
                max=facilities;
            }
        }
        if(max==0) return "";
        for(String HotelName: hoteldb.keySet()){
            if(max == hoteldb.get(HotelName).getFacilities().size()){
                hotelname.add(HotelName);
            }
        }
        Collections.sort(hotelname);
        return hotelname.get(0);
    }


    public int bookARoom( Booking booking){

        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there are not enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid
        UUID uuid=UUID.randomUUID();
        String bookingId=String.valueOf(uuid);
        booking.setBookingId(bookingId);
        bookingroomdb.put(bookingId,booking);
        int priceperroom = hoteldb.get(booking.getHotelName()).getPricePerNight();
        int no_of_rooms = booking.getNoOfRooms();
        if(no_of_rooms>hoteldb.get(booking.getHotelName()).getAvailableRooms()){
            return -1;
        }
        booking.setAmountToBePaid(priceperroom*no_of_rooms);
        return booking.getAmountToBePaid();
    }


    public int getBookings(Integer aadharCard)
    {
        //In this function return the bookings done by a person
        //count return
        int count = 0;
        for(String bookingId : bookingroomdb.keySet()){
            count++;
        }
        return count;
    }


    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName){

        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible
        Hotel hotel = hoteldb.get(hotelName);
        List<Facility> facilities = hotel.getFacilities();
        HashSet setlist=new HashSet(facilities);
        for(Facility facility: newFacilities){
            if(facilities.contains(facility)==false){
                facilities.add(facility);
            }
        }
        List<Facility> facilityList=new ArrayList<>(setlist);
        hotel.setFacilities(facilityList);
        return hotel;
    }

}

