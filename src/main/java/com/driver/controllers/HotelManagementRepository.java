package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {
    Map<String, Hotel> hotelMap = new HashMap<>(); //hotelName -> hotel
    Map<Integer, User> userMap = new HashMap<>(); // adharNo -> user
    Map<String, Booking> bookingMap = new HashMap<>(); //BookingId -> booking
    Map<String, Integer> userRent = new HashMap<>(); // BookingId -> amountToBePaid
    public String addHotel(Hotel hotel) {
        if (hotel.getHotelName() == null)return "FAILURE";
        if (hotelMap.containsKey(hotel.getHotelName()))return "FAILURE";
        String hotelName = hotel.getHotelName();
        hotelMap.put(hotelName, hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        int adharNum = user.getaadharCardNo();
        userMap.put(adharNum, user);
        return adharNum;
    }

    public String getHotelWithMostFacilities() {
        int maxFacility = 0;
        for (String key : hotelMap.keySet()) {
            List<Facility> facilities = hotelMap.get(key).getFacilities();
            maxFacility = Math.max(maxFacility, facilities.size());
        }

        if (maxFacility == 0) return "";
        List<String> hotelNames = new ArrayList<>();
        for (String key : hotelMap.keySet()) {
            List<Facility> facilities = hotelMap.get(key).getFacilities();
            if (facilities.size() == maxFacility) hotelNames.add(key);
        }
        Collections.sort(hotelNames);
        return hotelNames.get(0);
    }

    public int bookRoom(Booking booking) {
        String hotelName = booking.getHotelName();
        if (!hotelMap.containsKey(hotelName))return -1;
        if (hotelMap.get(hotelName).getAvailableRooms() >= booking.getNoOfRooms()) {
            Hotel hotel = hotelMap.get(hotelName);
            int totalRoomAvilable = hotel.getAvailableRooms();
            totalRoomAvilable -= booking.getNoOfRooms();
            hotel.setAvailableRooms(totalRoomAvilable);
            hotelMap.put(hotelName, hotel);
            String bookingId = UUID.randomUUID() + "";
            System.out.println(bookingId + "bookingId");
            int amountTobePaid = hotel.getPricePerNight() * booking.getNoOfRooms();
            bookingMap.put(bookingId, booking);
            userRent.put(bookingId, amountTobePaid);
            System.out.println(amountTobePaid + "Amount To Paid");
            return amountTobePaid;
        }
        return -1;
    }

    public int getBookings(Integer aadharCard) {
        int cnt = 0;
        for (String key : bookingMap.keySet()) {
            if (aadharCard.equals(bookingMap.get(key).getBookingAadharCard()))cnt++;
        }
        return cnt;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        if (!hotelMap.containsKey(hotelName))return null;
        Hotel hotel = hotelMap.get(hotelName);
        List<Facility> facilities = hotel.getFacilities();
        for (int i = 0; i < newFacilities.size(); i++) {
            if (!facilities.contains(newFacilities.get(i)))facilities.add(newFacilities.get(i));
        }
        hotel.setFacilities(facilities);
        hotelMap.put(hotelName, hotel);
        return hotel;
    }
}