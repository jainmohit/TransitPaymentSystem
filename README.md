# Project Details

Before boarding a bus at a bus stop, passengers tap their credit card (identified by the PAN, or Primary Account
Number) on a card reader. This is called a tap on. When the passenger gets off the bus, they tap their card
again. This is called a tap off. The amount to charge the passenger for the trip is determined by the stops where
the passenger tapped on and tapped off. The amount the passenger will be charged for the trip will be
determined as follows:
```
Trips between Stop 1 and Stop 2 cost $3.25
Trips between Stop 2 and Stop 3 cost $5.50
Trips between Stop 1 and Stop 3 cost $7.30
```
Note that the above prices apply for travel in either direction (e.g. a passenger can tap on at stop 1 and tap
off at stop 2, or they can tap on at stop 2 and can tap off at stop 1. In either case, they would be charged
$3.25).

##### Completed Trips
If a passenger taps on at one stop and taps off at another stop, this is called a complete trip. The amount the
passenger will be charged for the trip will be determined based on the table above. For example, if a passenger
taps on at stop 3 and taps off at stop 1, they will be charged $7.30.

##### Incomplete trips
If a passenger taps on at one stop but forgets to tap off at another stop, this is called an incomplete trip. The
passenger will be charged the maximum amount for a trip from that stop to any other stop they could have
travelled to. For example, if a passenger taps on at Stop 2, but does not tap off, they could potentially have
travelled to either stop 1 ($3.25) or stop 3 ($5.50), so they will be charged the higher value of $5.50.

##### Cancelled trips
If a passenger taps on and then taps off again at the same bus stop, this is called a cancelled trip. The
passenger will not be charged for the trip.

The problem
Given an input file in CSV format containing list of taps (ON and OFF) in separate lines, you will need to create
an output file containing trips made by customers.
taps.csv [input file]
You are welcome to assume that the input file is well formed and is not missing data.
Example input file:
```
ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
1, 22-01-2023 13:00:00, ON, Stop1, Company1, Bus37, 5500005555555559
2, 22-01-2023 13:05:00, OFF, Stop2, Company1, Bus37, 5500005555555559
3, 22-01-2023 09:20:00, ON, Stop3, Company1, Bus36, 4111111111111111
4, 23-01-2023 08:00:00, ON, Stop1, Company1, Bus37, 4111111111111111
5, 23-01-2023 08:02:00, OFF, Stop1, Company1, Bus37, 4111111111111111
6, 24-01-2023 16:30:00, OFF, Stop2, Company1, Bus37, 5500005555555559
```
##### trips.csv [output file]

You will need to match the tap ons and tap offs to create trips. You will need to determine how much to charge
for the trip based on whether it was complete, incomplete or cancelled and where the tap on and tap off
occurred. You will need to write the trips out to a file called trips.csv.
```
Example output file (for Tap IDs 1 &2) :
Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status
22-01-2018 13:00:00, 22-01-2018 13:05:00, 900, Stop1, Stop2, $3.25, Company1, Bus37, 5500005555555559, COMPLETED
```
### TransitPaymentSystem
Transit Bus system for payment reconciliation as per below statement

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Assumptions](#assumptions)
- [Solution](#solution)

## Installation

I have set up the project in intellij and all the code is written using the same ide. It's a transit app system for Java application.

## Usage

Run the Java main class as **<span style="color:green">TransitApp.java</span>** without any arguments as it can be modified using the arguments by passing the csv file name but for
the input file I have created a folder location as ```csvFiles``` in root folder with ```input```  as input folder for ```taps.csv``` and the ```output```
as output folder for output file created as ```trips.csv```

## Assumptions

I have assumed that if I found a TAP OFF without a TAP ON I will be ignoring the same and logging into the log file.

## Solution

I have used the sorting of the records using the datetime and the pan details as it will help me iterate over the records for a particular PanId.
**<span style="color:green">TripProcessor</span>** is the class responsible used to segregate the taps for different panId and sorting by DateTime and also creating the trips. 
I have two main public method as ```processTrips``` and ```processTripsV2``` as both works same but ```processTripsV2``` I was unable to test thoroughly. 



