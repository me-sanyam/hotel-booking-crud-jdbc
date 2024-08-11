package com.hotel_booking;

import java.util.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;


public class Main {

    private static String url = "";
    private static String username = "";
    private static String password = "";

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            while(true){
                System.out.println("---------------- Welcome to Hotel bookings ---------------------");
                System.out.println("Please select one Below ->");
                System.out.println("1. Book a room");
                System.out.println("2. View booking details");
                System.out.println("3. Update Booking");
                System.out.println("4. Delete Booking");
                System.out.println("5. Exit.");
    
                System.out.print("Please Enter your option : ");
                Scanner sc = new Scanner(System.in);
                int input = sc.nextInt();
                
                if(input == 5){
                    System.out.print("Thank you! Exited out of system");
                    break;
                }else if(input == 1){
                    bookRoom(conn,sc);
                }else if(input == 2){
                    viewBookings(conn,sc);
                }else if(input == 3){
                    UpdateBookings(conn,sc);
                }else{
                    DeleteBookings(conn,sc);
                }
            }

            conn.close();
        }catch(SQLException e){
            System.out.println("Error in connecting : "+e.getMessage());
        }
    }

    private static void bookRoom(Connection connection, Scanner inp){
        String query = "INSERT INTO bookings (name, phone, room) VALUES (?, ?, ?);";
        try {
            System.out.println("Please enter your name: ");
            String name = inp.next();
            System.out.println("Please enter your phone number: ");
            String phone = inp.next();
            System.out.println("Please enter the room number: ");
            int room = inp.nextInt();
            
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,name);
            st.setString(2,phone);
            st.setInt(3,room);

            int rowsEffected = st.executeUpdate();
            if(rowsEffected > 0){
                System.out.println("Room Booked Successfully.");
            }else{
                System.out.println("Room Booking Failed.");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewBookings(Connection connection, Scanner inp){
        String query = "SELECT * FROM bookings WHERE name = ?;";
        try {
            System.out.println("Please enter your name: ");
            String name = inp.next();
            
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,name);

            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int room = rs.getInt("room");
                String username = rs.getString("name");
                String phone = rs.getString("phone");
                System.out.println("Your Booking are: "+room+" "+username+" "+phone+" ");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }


    private static void UpdateBookings(Connection connection, Scanner inp){
        String query = "UPDATE bookings SET phone = ? WHERE id = ?;";
        try {
            System.out.println("Please enter your new phone no: ");
            String phone = inp.next();
            System.out.println("Please enter your Bookign id: ");
            int id = inp.nextInt();
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,phone);
            st.setInt(2,id);

            int rowsEffected = st.executeUpdate();
            if(rowsEffected > 0){
                System.out.println("Booking Updated Successfully.");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static void DeleteBookings(Connection connection, Scanner inp){
        String query = "DELETE FROM bookings WHERE id = ?;";
        try {
            System.out.println("Please enter your Bookign id: ");
            int id = inp.nextInt();
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1,id);

            int rowsEffected = st.executeUpdate();
            if(rowsEffected > 0){
                System.out.println("Booking Deleted Successfully.");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

}