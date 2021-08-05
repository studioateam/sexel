package net.aydini.sexel;

import java.util.ArrayList;
import java.util.List;

import net.aydini.sexel.reader.SexelReader;

public class Application {

	
	
	public static void main(String[] args) throws Exception {
		
		
		Aydin aydin = new Aydin();
		aydin.setName("aydin");
		aydin.setFamily("family");
		aydin.setAge(20);
		
		
		Aydin aydin2 = new Aydin();
		aydin2.setName("aydin");
		aydin2.setFamily("family");
		aydin2.setAge(23);
		
		Aydin aydin3 = new Aydin();
		aydin3.setName("آیدین");
		aydin3.setFamily("نصراله پور");
		aydin3.setAge(29);
		
		
		List<Aydin> aydins = new ArrayList<>();
		aydins.add(aydin2);
		aydins.add(aydin);
		aydins.add(aydin3);
		aydins.add(aydin3);
		aydins.add(aydin3);
		
//		new SexelWriter().setFilePath("a.xlsx").addSheetData(aydins).write();
		
		
		
		new SexelReader().setFilePath("a.xlsx").setOutputClass(Aydin.class).setSheetName("Aydin").doRead().forEach(System.out::println);
	}
}
