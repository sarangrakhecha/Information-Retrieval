import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class assignmentP1 {
	public assignmentP1() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		stringOperations strop = new stringOperations();
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

		File inFile = null;
		if (0 < args.length) {
			inFile = new File(args[0]);
		} else {
			System.err.println("Invalid arguments count:" + args.length);
			System.exit(0);
		}

		BufferedReader br = null;
		try {
			String sCurrentLine;

			// String[] words = null;

			br = new BufferedReader(new FileReader(inFile));
			ArrayList<String> ar = new ArrayList<String>();
			while ((sCurrentLine = br.readLine()) != null) {
				String[] words = sCurrentLine.split(" ");
				for (String str : words) {
					if (str.isEmpty() || ((str.startsWith("<") && str.endsWith(">"))))
						continue;
					else {
						str = str.toLowerCase();

						if (!(stopWordsSet.contains(str)))
							ar.add(str);
					}

				}
			}

			strop.wordType(ar);

		}

		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		strop.display(args);
	}

}
