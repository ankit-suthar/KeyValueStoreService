import com.key.value.storage.enums.Operation;
import com.key.value.storage.model.ValueObject;
import com.key.value.storage.print.ConsolePrint;
import com.key.value.storage.print.Print;
import com.key.value.storage.service.RecoveryService;
import com.key.value.storage.service.StorageService;

import java.io.IOException;
import java.time.Instant;
import java.util.Scanner;

public class Main {

    public void startDatabase() throws IOException {
        //Recovering the db
        RecoveryService recoveryService = new RecoveryService();
        recoveryService.buildDbFromFile();

        StorageService storageService = new StorageService();
        Print print = new ConsolePrint();

        Scanner sc = new Scanner(System.in);

        while(true) {
            String key = sc.nextLine();
            String value = sc.nextLine();
            Long ts = Instant.now().getEpochSecond();
            ValueObject valueObject = createObject(value, ts);
            String operation = sc.nextLine();

            switch (Operation.valueOf(operation)) {
                case ADD:
                    storageService.add(key, valueObject);
                    break;
                case UPDATE:
                    storageService.update(key, valueObject);
                    break;
                case DELETE:
                    storageService.delete(key, ts);
                    break;
                case READ:
                    Object result = storageService.read(key);
                    print.printData(String.valueOf(result));
                    break;
            }
        }
        // br.close();
    }

    private ValueObject createObject(String value, Long ts) {
        ValueObject valueObject = new ValueObject();
        valueObject.setValueMapper(null);
        valueObject.setTimestamp(ts);

        return valueObject;
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();

        main.startDatabase();
    }
}