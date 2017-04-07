import java.io.File;

public class DirectParser {

	public static void main(String[] args) {
		
		File file = new File("E:/Master/2. UFRT/4. XML & Web Technology/OWL-S WEB SERVICES/OWL-S WEB SERVICES/Services/economy-car_price_service.owls/amount-of-moneycar_pricecompany_service.owls");
		Parser parser = new Parser();
        parser.parse(file);
        
        //This is where a real application would open the file.
        System.out.println("Opening: " + file.getParent() + ".\n");
	}

}
