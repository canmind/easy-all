package com.easy.common.core.builder.freemarker;

import java.io.File;

public class FileUtil {
	public static void createDir(String path) {
		if (null != path && !"".equals(path)) {
			File file = new File(path);
			file.mkdirs();
		}
	}

	public static void initDirName(Params params) {
		// 1.po
		String poDir = params.getOsdir() + File.separatorChar + "po";
		createDir(poDir);
		// 2.vo
		String voDir = params.getOsdir() + File.separatorChar + "vo";
		createDir(voDir);
		// 3.xml
		String xmlDir = params.getOsdir() + File.separatorChar + "xml";
		createDir(xmlDir);
	}
}
