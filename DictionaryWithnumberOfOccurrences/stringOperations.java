import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class stringOperations {

	TreeMap<String, objects> dict = new TreeMap<String, objects>();

	Set<String> keys = dict.keySet();
	int dummy_value = 0;

	public stringOperations() {
		// TODO Auto-generated constructor stub
	}

	public void wordType(ArrayList<String> arr) {
		// TODO Auto-generated method stub
		String[] wordsList = null;

		wordsList = (String[]) arr.toArray(new String[arr.size()]);

		for (int i = 0; i < wordsList.length; i++) {
			if (wordsList[i].contains("-")) {
				String strArray[] = wordsList[i].split("-");
				int x=strArray.length;
				int j=0;
				while(j<x)
				{
					String strword=strArray[j];
					StringTrimOperation(strArray[j]);
					j++;
				}
				//String fhalf = strArray[0];
				//String shalf = strArray[1];

				//StringTrimOperation(fhalf);
				//StringTrimOperation(shalf);
			}

			else {
				StringTrimOperation(wordsList[i]);

			}
		}

	}

	public void StringTrimOperation(String singleword) {

		if (singleword.startsWith("(") || singleword.startsWith("[") || singleword.startsWith("\'")
				|| singleword.startsWith("\"")) {
			singleword = singleword.substring(1, singleword.length());
		}

		if (singleword.endsWith(")") || singleword.endsWith("]") || singleword.endsWith("\'")
				|| singleword.endsWith(" \" ") || singleword.endsWith("?") || singleword.endsWith(",")
				|| singleword.endsWith(".") || singleword.endsWith("!") || singleword.endsWith(";")
				|| singleword.endsWith(":")) {
			singleword = singleword.substring(0, singleword.length() - 1);
		}

		if (singleword.endsWith("ies") && !(singleword.endsWith("eies")) && !(singleword.endsWith("aies"))) {
			singleword = singleword.replace("ies", "y");
		}

		if (singleword.endsWith("es") && !(singleword.endsWith("aes")) && !(singleword.endsWith("oes"))) {
			singleword = singleword.replace("es", "e");
		}

		if (singleword.endsWith("\'s")) {
			singleword = singleword.substring(0, singleword.length() - 2);
		}

		if (singleword.endsWith("/s'")) {
			singleword = singleword.substring(0, singleword.length() - 2);
		}

		if (singleword.endsWith("s")) {
			singleword = singleword.substring(0, singleword.length() - 1);
		}

		if (singleword.length() <= 1) {
			singleword = singleword.substring(0, 0);
			return;

		}

		if (singleword.endsWith("thi")) {
			singleword = singleword.replace("thi", "this");
		}

		if (singleword.endsWith("doe")) {
			singleword = singleword.replace("doe", "does");
		}
		insert(singleword);

	}

	public void insert(String newword) {

		if (dict.containsKey(newword)) {
			objects o = dict.get(newword);
			o.count = o.count + 1;
			dict.put(newword, o);
		} else {
			dict.put(newword, new objects(1));
		}

	}

	public void display(String[] args) {
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(args[1]));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("TOKENS \t\t COUNT");
		
		for (String key : keys) {
			objects op = dict.get(key);
			out.println(String.format("%-15s  %-10s%n", key, op.count));
		}
	}
}
