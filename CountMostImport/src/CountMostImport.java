import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class CountMostImport {
	String dirName;
	HashMap<String, Integer> importClassRecords;
	
	public static void main(String[] args) {
		System.out.println("请输入要统计的文件路径");
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        //filePath = "src/";
		CountMostImport cmi = new CountMostImport(filePath);
		System.out.println(cmi.getTop10());
	}

	public CountMostImport(String dir) {
		this.dirName = dir;
		importClassRecords = new HashMap<String, Integer>();
		this.statisticsClazz(new File(this.dirName));
	}

	public void processFile(File file) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("public") || line.startsWith("class")) {
					break;
				}
				if (line.startsWith("import")) {
					String className = line.substring(6, line.length() - 1)
							.trim();
					Integer value = importClassRecords.get(className);
					if (value == null) {
						importClassRecords.put(className, 1);
					} else {
						importClassRecords.put(className, value + 1);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void statisticsClazz(File file) {
		if (!file.isDirectory()) {
			processFile(file);
		} else {
			File[] files = file.listFiles();
			for (File tmpFile : files) {
				statisticsClazz(tmpFile);
			}
		}

	}

	public List<String> getTop10() {

		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(
				importClassRecords.entrySet());
		Comparator<Entry<String, Integer>> c = new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o2.getValue() - o1.getValue();
			}
		};
		Collections.sort(list, c);
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < 10 && i < list.size(); i++) {
			result.add(list.get(i).getKey());
		}
		return result;
	}

}
