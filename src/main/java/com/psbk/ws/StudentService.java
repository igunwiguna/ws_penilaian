package com.psbk.ws;

import com.psbk.ws.Student;
import com.psbk.ws.common.MasterConnection;
import com.psbk.ws.common.MyMap;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;

@Path("/student")
public class StudentService extends MasterConnection{
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(){
		Student s = new Student();
		
		s.setId("143040001");
		s.setName("John Doe");
		s.setAddress("Gotham City");
		
		return s;
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createStudent(Student student){
		String respon = "Data Saved : "+student;
		
		return Response.status(201).entity(respon).build();
	}
        
        @GET
        @Path("{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Map getStudentByID(@PathParam("id") Integer id){
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("statusId", "1");
            result.put("Pesan", "inquiry berhasil");
            System.out.println("id: "+id);
            try {
                createConnection();
                MyMap student =(MyMap) jt.queryObject("select * from student where id= ?", new Object[] {id}, new MyMap());
                closeConnection();
                if(student != null){
                    result.put("result", student);
                }
            } catch (Exception e) {
                result.put("pesan", "Gagal karena : " + e.getMessage());
            }
            
            return result;
        }
        
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/insert")
        public Object createStd(@Context HttpServletRequest hsr){
            StringBuffer sb = new StringBuffer();
            String line = null;
            JSONObject request = null;
            MyMap respon = new MyMap();
            MyMap data = new MyMap();
            DataInputStream in;
            try {
                createConnection();
                in = new DataInputStream(new BufferedInputStream(hsr.getInputStream()));
                
                while((line = in.readLine()) != null)
                    sb.append(line);
                JSONObject json = new JSONObject(sb.toString());
                request = (JSONObject) json.get("request");
                
                if(request==null){
                    respon.put("pesan", "data yang dikirim tidak ditemukan");
                    respon.put("rCode", "99");
                    respon.put("studentId", "0");
                    return respon;
                }
                data.put("name", request.getString("name"));
                data.put("address", request.getString("address"));
                
                jt.insert("student", data);
                respon.put("pesan", "data berhasil disimpan");
                respon.put("rCode", "00");
                respon.put("StudentId", "1");
            } catch (Exception e) {
                e.printStackTrace();
                respon.put("pesan", e.getMessage());
                respon.put("rCode", "99");
                respon.put("studentId", "0");
            }
            return respon;
        }

}
