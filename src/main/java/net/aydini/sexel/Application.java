package net.aydini.sexel;

import java.util.ArrayList;
import java.util.List;

import net.aydini.sexel.writer.SexelWriter;

public class Application {

	
	
	public static void main(String[] args) throws Exception {
		
		
		Aydin aydin = new Aydin();
		aydin.setName("aydin");
		aydin.setFamily("family");
		aydin.setAge(30);
		
		
		Aydin aydin2 = new Aydin();
		aydin2.setName("aydin");
		aydin2.setFamily("family");
		aydin2.setAge(28);
		
		
		Aydin aydin3 = new Aydin();
		aydin3.setName("aydin2");
		aydin3.setFamily("family2");
		aydin3.setAge(29);
		
		
		List<Aydin> aydins = new ArrayList<>();
		aydins.add(aydin2);
		aydins.add(aydin);
		aydins.add(aydin3);
		aydins.add(aydin3);
		aydins.add(aydin3);
		
		new SexelWriter().setFilePath("a.xlsx").addSheetData(aydins).addSheetData(aydins).write();
		
		
	}
}
