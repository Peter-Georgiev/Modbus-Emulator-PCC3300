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
       
For Windows PowerShell console.
To check your Windows PowerShell version, the easiest and most reliable method is to use the $PSVersionTable command within the PowerShell console.
For PowerShell 5.1+:
  Run these commands before executing your Java program:
    chcp 65001                             # Sets console to UTF-8
    [Console]::OutputEncoding = [System.Text.Encoding]::UTF8  # Forces UTF-8 output
    java Modbus                         # Run your Java program
  For Older PowerShell Versions:
    $OutputEncoding = [System.Text.Encoding]::UTF8
    chcp 65001
    java Modbus                         # Run your Java program
