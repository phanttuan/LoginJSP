package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import jakarta.servlet.http.HttpServletRequest;

public class FileUploadHelper {
    @Deprecated
    public static List<FileItem> parseRequest(HttpServletRequest request) throws FileUploadException {
        throw new FileUploadException("FileUploadHelper.parseRequest is deprecated on Jakarta. Use Servlet Part API (req.getPart) instead.");
    }
    
    public static String getFieldValue(List<FileItem> items, String fieldName, String encoding) {
        for (FileItem item : items) {
            if (item.isFormField() && item.getFieldName().equals(fieldName)) {
                try {
                    return item.getString(encoding);
                } catch (Exception e) {
                    return item.getString();
                }
            }
        }
        return null;
    }
    public static FileItem getFileItem(List<FileItem> items, String fieldName) {
        for (FileItem item : items) {
            if (!item.isFormField() && item.getFieldName().equals(fieldName)) {
                return item;
            }
        }
        return null;
    }
    
    public static Map<String, String> getFormFieldValues(List<FileItem> items, String encoding) {
        Map<String, String> values = new HashMap<>();
        
        for (FileItem item : items) {
            if (item.isFormField()) {
                try {
                    values.put(item.getFieldName(), item.getString(encoding));
                } catch (Exception e) {
                    values.put(item.getFieldName(), item.getString());
                }
            }
        }
        
        return values;
    }
    
    public static String saveFile(FileItem fileItem, String destDir, String fileName) throws IOException {
        if (fileItem == null || fileItem.getSize() == 0) {
            return null;
        }
        
        // Nếu không có tên file, dùng tên gốc từ client
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = new File(fileItem.getName()).getName();
        }
        
        // Tạo thư mục đích nếu chưa tồn tại
        File destDirectory = new File(destDir);
        if (!destDirectory.exists()) {
            destDirectory.mkdirs();
        }
        
        // Tạo file mới
        File destFile = new File(destDirectory, fileName);
        
        // Lưu file
        try {
            fileItem.write(destFile);
        } catch (Exception e) {
            throw new IOException("Không thể lưu file", e);
        }
        
        return destFile.getAbsolutePath();
    }
}