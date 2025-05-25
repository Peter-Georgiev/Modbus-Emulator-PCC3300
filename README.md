Simple Modbus RTU/TCP Emulator for PowerCommand® 3.3 (PCC3300).
How to start a java file Modbus.java:
  Solution 1: Move the File to the Correct Folder:
    1. Create the required folder structure:
      mkdir -p com/mycompany/modbus
    2. Move Modbus.java into it:
      move .\Modbus.java .\com\mycompany\modbus\
    3. Compile from the root directory:
      javac .\com\mycompany\modbus\Modbus.java
    4. Run file Modbus
       java com.mycompany.modbus.Modbus
  Solution 2: Remove the Package Declaration (Temporary Fix):
    1. Open Modbus.java and remove:
      package com.mycompany.modbus;
    2. Then compile directly:
      javac .\Modbus.java
    3. Run file Modbus
       java Modbus
