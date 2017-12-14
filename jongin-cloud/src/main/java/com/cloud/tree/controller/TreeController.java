package com.cloud.tree.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.repository.vo.Tree;
import com.cloud.tree.service.TreeService;

@Controller
@RequestMapping("/tree")
public class TreeController {

	@Autowired
	TreeService service;

	
    //ff 폴더 안 모든 파일 및 폴더 검색
	List<Tree> pullFile(String path) {
        File[] list = new File(path).listFiles(); 
        List<Tree> trees = new ArrayList<>();
        
        try{
            for (File ff : list) {
            	Tree tree = new Tree();
            	
            	Date updateDate = new Date(ff.lastModified());
            	tree.setUpdateDate(updateDate);
            	
                if (ff.isFile()) {
                	tree.setTitle(ff.getName());
                	tree.setPath(ff.toString());
                	System.out.println("파일 : "+ff.getName());
                	if(ff.getName().lastIndexOf(".")!=-1) {
                		tree.setExt(ff.getName().substring(ff.getName().lastIndexOf(".")+1));
                		System.out.println("확장자 : "+ff.getName().substring(ff.getName().lastIndexOf(".")+1));
                	}
                }else if (ff.isDirectory()) {
                	tree.setTitle(ff.getName());
                	tree.setIsFolder(true);
                	tree.setIsLazy(true);
                	tree.setPath(ff.toString());
                	System.out.println("폴더 : "+ff.getName());
                	
                    //pullFile(ff.toString());
                }
                trees.add(tree);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return trees;
    }
	
	void deleteFile(String path) {
		
		File file = new File(path);
		File [] fList = file.listFiles();

		for (File f : fList) {			
			if(!f.isDirectory()) {
				f.delete();
			}else {
				deleteFile(f.toString());
			}
		}
		
		file.delete();
		
	}
    
	@RequestMapping("/sublist.json")
	@ResponseBody
	public List<Tree> subTree(String path) throws Exception {
		
		System.out.println(path);
		
		List<Tree> trees = pullFile(path);
		System.out.println(trees.toString());
		
		return trees;
				
	}
	

	@RequestMapping("/list.json")
	@ResponseBody
	public Tree rootTree(String user) throws Exception {
		
		String path = "C:\\tree\\"+user;
		
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		//폴더 만들기
		
		List<Tree> trees = pullFile(path);
		Tree tree = new Tree();
		
		Date updateDate = new Date(f.lastModified());
		
		System.out.println(trees.toString());
		tree.setTitle(user);
		tree.setIsFolder(true);
		tree.setPath(path);
		tree.setIsLazy(true);
		tree.setChildren(trees);
		tree.setUpdateDate(updateDate);
		
		return tree;
				
	}
	
	
	@RequestMapping("/newfolder.json")
	@ResponseBody
	public Map<String, Object> newfolder(String path, String name) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("dup", true);
		
		File f = new File(path+"\\"+name);
		if(!f.exists()) {
			map.put("dup", false);
			f.mkdirs();
		}
		
		return map;
	}
	
	@RequestMapping("/filedelete.json")
	@ResponseBody
	public void fileDelete(String path) throws Exception {

		deleteFile(path);
	}
	
	@RequestMapping("/filerename.json")
	@ResponseBody
	public Map<String, Object> fileRename(String path, String rename) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		File f = new File(path);
		Path file = Paths.get(path);

		String ext = "";
		
		if(f.isDirectory()) {
			path = path.substring(0, path.lastIndexOf("\\")+1)+rename;
		}else {
			ext = f.getName().substring(f.getName().lastIndexOf("."));
			path = path.substring(0, path.lastIndexOf("\\")+1)+rename+ext;
		}
		
		System.out.println("S : "+path);
		
		try {
			Files.move(file , Paths.get(path));
			map.put("result", true);
		} catch (Exception e) {
			System.out.println(e);
			map.put("result", false);
		}
		return map;
	}
	
	
	@RequestMapping("/filemove.json")
	@ResponseBody
	public Map<String, Object> fileMove(String moveFilePath, String recFilePath) throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		System.out.println("옮길 것 : "+moveFilePath);
		
		Path moveF = Paths.get(moveFilePath);
		File recF = new File(recFilePath);
		
		if(!recF.isDirectory()) {
			map.put("result", false);
			return map;
		}
		
		Path recPath = Paths.get(recFilePath+"\\"+moveF.getFileName());
		System.out.println("옮겨질 곳 : "+recPath);
		
		try {
			Files.move(moveF , recPath);
			map.put("result", true);
			map.put("path", ""+recPath);
		} catch (Exception e) {
			System.out.println(e);
			map.put("result", false);
		}
		
		return map;
		
	}
}
