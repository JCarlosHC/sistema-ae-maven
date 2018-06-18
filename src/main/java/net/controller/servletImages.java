package net.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import net.dao.ConnectionDB;
import net.dao.examDAO;
import net.model.exam;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class servletImages
 */
public class servletImages extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private static final String DATA_DIRECTORY = "media";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletImages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String campoName, filePath, msg, url, fileName = null, success;
        int id = 0;
        RequestDispatcher rd;
        SimpleDateFormat formato = new SimpleDateFormat("yyyymmddHHmmss");
        Date fecha_actual = new java.util.Date();
        ConnectionDB cn = new ConnectionDB();
        examDAO exDao = new examDAO(cn);
        exam model = new exam();
        exam modelresp;
        
        if (!isMultipart) {
            return;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to
        // disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // constructs the folder where uploaded file will be stored
        String uploadFolder = getServletContext().getRealPath("");

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);

        try {
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    fileName = new File(item.getName()).getName();
                    fileName = DATA_DIRECTORY + "/" + formato.format(fecha_actual) + fileName;
                    filePath = uploadFolder + File.separator + fileName;
                    File uploadedFile = new File(filePath);
                    System.out.println(filePath);
                    // saves the file to upload directory
                    item.write(uploadedFile);

                } else {
                    campoName = item.getFieldName();
                    if (campoName.equals("id")) {
                        id = Integer.parseInt(item.getString());
                    }
                }
            }
            model.setId(id);
            model.setImage(fileName);
            if (exDao.updateImage(model)) {
                msg = "Se guardo correctamente la imagen";
                success = "true";
                modelresp = exDao.getExam(model.getId());
                url = "exam.jsp";
            } else {
                msg = "Ocurrio un error, la imagen no fue guardada";
                success = "false";
                modelresp = new exam(0,"","",0,null,null,0,0, 0,0, null);
                url = "exam.jsp";
            }
            request.setAttribute("msg", msg);
            request.setAttribute("success", success);
            request.setAttribute("myExam", modelresp);
            
            rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            
        } catch (FileUploadException ex) {
            throw new ServletException(ex);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
