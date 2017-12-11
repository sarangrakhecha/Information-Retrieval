import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class stringOperations {

	int total = 0;

	TreeMap<String, objects> dict = new TreeMap<String, objects>();
	TreeMap<String, TreeMap<Integer, Integer>> post = new TreeMap<String, TreeMap<Integer, Integer>>();

	Set<String> keys = dict.keySet();

	int dummy_value = 0;

	public stringOperations() {
		// TODO Auto-generated constructor stub
	}

	public void wordType(ArrayList<String> arr, int doc) {
		// TODO Auto-generated method stub
		String[] wordsList = null;
		wordsList = (String[]) arr.toArray(new String[arr.size()]);

		for (int i = 0; i < wordsList.length; i++) {
			if (wordsList[i].contains("-")) {
				String strArray[] = wordsList[i].split("-");
				int x = strArray.length;
				int j = 0;
				while (j < x) {
					StringTrimOperation(strArray[j], doc);
					j++;
				}
			}

			else {
				StringTrimOperation(wordsList[i], doc);

			}
		}

	}

	public void StringTrimOperation(String singleword, int doc) {

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

		insert(singleword, doc);

	}

	public void insert(String newword, int doc) {
		total++;
		if (post.containsKey(newword)) {
			TreeMap<Integer, Integer> postoff1 = post.get(newword);
			if (postoff1.containsKey(doc)) {
				int count = (int) postoff1.get(doc) + 1;
				postoff1.put(doc, count);
				post.put(newword, postoff1);
				//System.out.println(post.put(newword, postoff1));
				
			} else {

				postoff1.put(doc, 1);
				post.put(newword, postoff1);
				// System.out.println(post.put(newword, postoff1));
			}
		} else {
			TreeMap<Integer, Integer> postoff4 = new TreeMap<Integer, Integer>();
			postoff4.put(doc, 1);
			post.put(newword, postoff4);
			// System.out.println(post.put(newword, postoff4));
		}

		if (dict.containsKey(newword)) {
			objects o = dict.get(newword);
			o.count = o.count + 1;
			if (o.df != doc) {
				o.df = o.df + 1;

			}

			dict.put(newword, o);
		} else {
			dict.put(newword, new objects(1, 1, 0));
		}
	}

	public void display(String[] args) throws FileNotFoundException {
		int offset = 0;
		for (String key : keys) {
			dict.get(key).offset = offset;
			offset = offset + dict.get(key).df;
		}

		PrintWriter pw2 = new PrintWriter(new File("dictionary.csv"));
		PrintWriter pw3 = new PrintWriter(new File("postings.csv"));
		PrintWriter pw4 = new PrintWriter(new File("total.txt"));
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		StringBuilder sb4 = new StringBuilder();

		sb4.append(total);
		pw4.write(sb4.toString());
		pw4.close();

		// ************PRINTING POSTING LIST INTO CSV FILE**********

		for (Map.Entry<String, TreeMap<Integer, Integer>> entry : post.entrySet()) {

			TreeMap<Integer, Integer> myTree = entry.getValue();

			for (Map.Entry<Integer, Integer> entry2 : myTree.entrySet()) {
				sb3.append(entry2.getKey());
				sb3.append(',');
				sb3.append(entry2.getValue());
				sb3.append('\n');
			}

		}
		pw3.write(sb3.toString());
		pw3.close();


		for (String key : keys) {
			objects op = dict.get(key);

			sb2.append(key);
			sb2.append(',');
			sb2.append(op.count);
			sb2.append(',');
			sb2.append(op.df);
			sb2.append(',');
			sb2.append(op.offset);
			sb2.append('\n');

		}
		pw2.write(sb2.toString());
		pw2.close();
	}
}
