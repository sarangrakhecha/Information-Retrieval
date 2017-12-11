import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class stringOperations {

	String inputquery = "";

	public ArrayList<String> wordProcessing(String[] finalin) {
		ArrayList<String> temp = new ArrayList<>();
		Set<String> stopWordsSet = new HashSet<String>();

		ArrayList<String> ar = new ArrayList<String>();

		stopWordsSet.add("and");
		stopWordsSet.add("a");
		stopWordsSet.add("the");
		stopWordsSet.add("an");
		stopWordsSet.add("by");
		stopWordsSet.add("from");
		stopWordsSet.add("for");
		stopWordsSet.add("hence");
		stopWordsSet.add("of");
		stopWordsSet.add("the");
		stopWordsSet.add("with");
		stopWordsSet.add("in");
		stopWordsSet.add("within");
		stopWordsSet.add("who");
		stopWordsSet.add("when");
		stopWordsSet.add("where");
		stopWordsSet.add("why");
		stopWordsSet.add("how");
		stopWordsSet.add("whom");
		stopWordsSet.add("have");
		stopWordsSet.add("had");
		stopWordsSet.add("has");
		stopWordsSet.add("not");
		stopWordsSet.add("for");
		stopWordsSet.add("but");
		stopWordsSet.add("do");
		stopWordsSet.add("does");
		stopWordsSet.add("done");

		for (String str : finalin) {
			if (str.isEmpty() || ((str.startsWith("<") && str.endsWith(">"))))
			{	
				
				continue;
			}
			else {
				str = str.toLowerCase();
				if (str.length() > 1) {
					if (!(stopWordsSet.contains(str))) {
						ar.add(str);
					}
				}
			}

		}

		String[] query = null;
		query = (String[]) ar.toArray(new String[ar.size()]);

		for (int i = 0; i < query.length; i++) {
			if (query[i].contains("-")) {
				String strArray[] = query[i].split("-");
				int x = strArray.length;
				int j = 0;
				while (j < x) {
					String tmp = stringProcess(strArray[j]);
					if(tmp != "")
						temp.add(tmp);
					j++;
				}
			}

			else {
				String tmp = stringProcess(query[i]);
				if(tmp != "")
					temp.add(tmp);

			}
		}
		return temp;
	}

	String stringProcess(String singleword) {

		singleword = singleword.replaceAll("[^a-zA-Z0-9]", "");

		if (singleword.startsWith("``") || singleword.startsWith("''")) {
			singleword = singleword.substring(2, singleword.length());
		}
		if (singleword.startsWith("(") || singleword.startsWith("[") || singleword.startsWith("\'")
				|| singleword.startsWith("\"")) {
			singleword = singleword.substring(1, singleword.length());
		}

		if (singleword.endsWith(".''") || singleword.endsWith(".'") || singleword.endsWith(",''")
				|| singleword.endsWith(";))")) {
			singleword = singleword.substring(0, singleword.length() - 3);
		}

		if (singleword.endsWith(";)") || singleword.endsWith("''") || singleword.endsWith(",''")
				|| singleword.endsWith(";))")) {
			singleword = singleword.substring(0, singleword.length() - 2);
		}

		if (singleword.endsWith(")") || singleword.endsWith("]") || singleword.endsWith("\'")
				|| singleword.endsWith(" \" ") || singleword.endsWith("?") || singleword.endsWith(",")
				|| singleword.endsWith(".") || singleword.endsWith("!") || singleword.endsWith(";")
				|| singleword.endsWith(":") || singleword.endsWith(".''")) {
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

		if (singleword.endsWith("thi")) {
			singleword = singleword.replace("thi", "this");
		}

		if (singleword.endsWith("doe")) {
			singleword = singleword.replace("doe", "does");
		}

		if (singleword.length() <= 1) {
			singleword = singleword.substring(0, 0);
			return "";
		}
		inputquery = singleword + " ";
		return inputquery;
	}
}
