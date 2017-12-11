import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class assignmentP2 {
	static List<String> paths = new ArrayList<String>();

	public static List<String> directory(File dir2) {
		File[] files = dir2.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				directory(file);
			} else {
				paths.add(file.getAbsolutePath());

			}
		}
		return paths;
	}

	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("docsTable.csv"));
		// TODO Auto-generated method stub
		// File dir = null;
		stringOperations stringop = new stringOperations();
		StringBuilder sb1 = new StringBuilder();

		Set<String> stopWordsSet = new HashSet<String>();

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

		File dir = new File(args[0]);

		BufferedReader br = null;
		String sCurrentLine;
		TreeMap<Integer, objects2> document = new TreeMap<Integer, objects2>();
		ArrayList<String> wordArray = new ArrayList<String>();

		int doc = 0;
		int tlength = 0;
		int tflag = 0;
		int hflag = 0;
		String fhead = null;
		String ftext = null;
		int count = 0;

		objects2 d = new objects2(0, "", "", "");

		List<String> paths = directory(dir);

		for (String path : paths) {

			tlength = 0;
			doc++;
			wordArray.clear();

			try {

				br = null;
				br = new BufferedReader(new FileReader(path));
				ArrayList<String> ar = new ArrayList<String>();
				while ((sCurrentLine = br.readLine()) != null) {

					String[] words = sCurrentLine.split(" ");

					for (String str : words) {
						wordArray.add(str);
						if (!(stopWordsSet.contains(str))) {
							tlength++;
						}

						if (str.isEmpty() || ((str.startsWith("<") && str.endsWith(">")))) {
							continue;
						}

						else {
							str = str.replaceAll("[^a-zA-Z0-9]", "");
							str = str.toLowerCase();

							if (str.length() > 1) {
								if (!(stopWordsSet.contains(str)))
									ar.add(str);
							}
						}

					}
				}

				stringop.wordType(ar, doc);

				if (document.containsKey(doc)) {
					d = document.get(doc);
					d.tlength = tlength;
					document.put(doc, d);
				} else {
					d = new objects2(0, "", "", "");
					d.tlength = tlength;
					document.put(doc, d);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			fhead = "";
			ftext = "";
			count = 0;
			for (int loop = 0; loop < wordArray.size(); loop++) {
				if (wordArray.get(loop).contains("<HEADLINE>")) {
					hflag = 1;
					continue;
				}
				if (wordArray.get(loop).contains("</HEADLINE>")) {
					hflag = 0;
				}
				if (hflag == 1) {
					fhead = fhead + " " + wordArray.get(loop);
				}
				if (wordArray.get(loop).contains("<TEXT>")) {
					tflag = 1;
					count++;
					continue;
				}
				if (wordArray.get(loop).contains("</TEXT>")) {
					tflag = 0;
				}
				if (tflag == 1 && count < 40) {
					ftext = ftext + " " + wordArray.get(loop);
					count++;
				}

			}

			if (document != null && document.containsKey(doc)) {
				d = document.get(doc);
				d.path = path;
				d.heading = fhead;
				d.text = ftext;
				document.put(doc, d);
			} else {
				d = new objects2(0, "", "", "");
				d.path = path;
				d.heading = fhead;
				d.text = ftext;
				document.put(doc, d);

			}

		}

		Set<Integer> keys1 = document.keySet();

		for (int key : keys1) {
			objects2 op1 = document.get(key);
			sb1.append(key);
			sb1.append(',');
			sb1.append(op1.heading.replaceAll(",", ""));
			sb1.append(',');
			sb1.append(op1.tlength);
			sb1.append(',');
			sb1.append(op1.text.replaceAll(",", ""));
			sb1.append(',');
			sb1.append(op1.path);
			sb1.append('\n');

		}

		pw.write(sb1.toString());
		pw.close();
		stringop.display(args);
	}

}
