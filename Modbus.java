package com.mycompany.modbus;
import java.util.Map;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;

/**
 * @author p.georgiev
 * Емулатор на Modbus с PowerCommand® 3.3.
 * Изисква Java 14+ ( https://openjdk.org/jeps/361 ).
 */
public class Modbus {
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));

        System.out.println("=== ОБХОЖДАНЕ НА ВСИЧКИ ВЪВЕДЕНИ РЕГИСТРИ ===");
        for (Map.Entry<Integer, Integer> entry : REGISTERS.entrySet()) {
            printRegisterInfo(entry.getKey(), entry.getValue());
        }
    }

   /**
    * Тук се симулира четене от регистър – замени го с реална Modbus комуникация
    * и връщането на отговор.
   */
    public static final Map<Integer, Integer> REGISTERS = new HashMap<>();
    static {
        // Адрес на регистъра 40010 - Current position of the generator set switch panel 
        // Off-Run-Auto switch as seen by the generator set control (Read Only)
        REGISTERS.put(40010, 1);    // Симулиран отговор Specifications: 1: Manual
        // Адрес на регистъра 40012 - This Modbus Register displays most recent Fault or Warning (Read Only)
        REGISTERS.put(40012, 1435); // Симулиран отговор Specifications: 1435 from Fault List
        // Адрес на регистъра 40013 - This register returns the Fault Type of the Fault Code. (Read Only)
        REGISTERS.put(40013, 1);    // Симулиран отговор Specifications: 1: Warning
        // Адрес на регистъра 40061 - Battery Voltage value (Read Only)
        REGISTERS.put(40061, 2550); // Симулиран отговор Specifications: 25.50 Vdc    
        // Адрес на регистъра 40062 - Monitor point for the Oil Pressure (Read Only)        
        REGISTERS.put(40062, 475);  // Симулиран отговор Specifications: 475 psi
        // Адрес на регистъра 40063 - Monitor point for the Oil Temperature (Read Only)
        REGISTERS.put(40063, 75);   // Симулиран отговор Specifications: 75C         
        // Адрес на регистъра 40064 - Monitor point for the Coolant Temperature (Read Only)
        REGISTERS.put(40064, 70);   // Симулиран отговор Specifications: 70C  
        // Адрес на регистъра 40068 - Average Engine Speed (Read Only)
        REGISTERS.put(40068, 1500); // Симулиран отговор Specifications: 1500 rpm
        // Адрес на регистъра 40184 - Generator set bus line frequency (Read Only)
        REGISTERS.put(40184, 5000); // Симулиран отговор Specifications: 50.00 Hz
        // Адрес на регистъра 40300 - Genset start stop control via Modbus (Read and Write)
        REGISTERS.put(40300, 0);  
        // Адрес на регистъра 40301 - Fault reset via Modbus (No logical) (Read and Write)        
        REGISTERS.put(40301, 0); 
    }
    
    /**
     * Event/Fault List - взети са от TABLE 185., page.400, 
     * документ - Service Manual PowerCommand® 3.3 0900-0670 (Issue 30 02-2025)
     */
    public static final Map<Integer, String> EVENT = new HashMap<>();
    static {
        // Код за грешка. Описание.
        //TABLE 185., page.400 - (Service Manual PowerCommand® 3.3 0900-0670 (Issue 30 02-2025))
        EVENT.put(0, "Няма грешки.");
        EVENT.put(334, "HT Coolant Temperature Incorrect.");
        EVENT.put(2646, "HT Coolant Temperature Condition Exists.");
        EVENT.put(2963, "HT Coolant Temperature Warning High.");
        EVENT.put(441, "Low Battery 1 Voltage.");
        EVENT.put(442, "High Battery 1 Voltage.");
        EVENT.put(359, "Fail To Start.");
        EVENT.put(1433, "Local Emergency Stop.");
        EVENT.put(1434, "Remote Emergency Stop.");
        EVENT.put(1435, "Low Coolant Temperature.");
        EVENT.put(1446, "High AC Voltage.");
        EVENT.put(1447, "Low AC Voltage.");
        EVENT.put(1448, "Underfrequency.");
        EVENT.put(1449, "Overfrequency.");
    }
    
    // Използва се само за адрес на регистъра 40010
    public static String stateSwitchPosition(int value) {
        return switch (value) {
            case 0 -> "Off: Генераторът е изключен.";
            case 1 -> "Manual: Генераторът е в ръчен режим.";
            case 2 -> "Auto: Генераторът е в автоматичен режим.";
            default -> "UNKNOWN: Неизвестна стойност = " + value + ".";
        };
    }

    // Използва се само за адрес на регистъра 40013
    public static String stateActiveFaultType(int value) {
        return switch (value) {
            case 0 -> "None: Няма грешки.";
            case 1 -> "Warning: Предупреждение.";
            case 2 -> "Shutdown: Изключен.";
            default -> "UNKNOWN: Неизвестна стойност = " + value + ".";
        };
    }

    // GET - Event/Fault List
    public static String getActiveFaultCodeDescription(int faultCode) {
        return EVENT.getOrDefault(faultCode, "Неизвестна грешка (CODE: " + faultCode + ").");
    }
    
    /**
     * Използва се само за адрес на регистъра 40012.
     * Стоиностите се взимат от (getActiveFaultCodeDescription), GET - Event/Fault List.
     */
    public static String stateActiveFaultCode(int faultCode) {
        String codeMessage = getActiveFaultCodeDescription(faultCode);
        return "CODE - " + faultCode + " " + codeMessage;
    }
    
    // За целите на симулацията
    public static void printRegisterInfo(int address, int code) {
        if (address == 40010) {
            System.out.println(address + ": " + stateSwitchPosition(code));
        } else if (address == 40012) {
            System.out.println(address + ": " + stateActiveFaultCode(code));
        } else if (address == 40013) {
            System.out.println( address + ": " + stateActiveFaultType(code));
        } else {
            System.out.println(address + ": Стойност = " + code);
        }
    }
}
