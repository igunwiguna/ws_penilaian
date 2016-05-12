
package com.psbk.ws;

import com.psbk.ws.common.MasterConnection;
import com.psbk.ws.common.MyMap;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONObject;

@Path("/PengaturanNilai")
public class PersentasiNilaiService extends MasterConnection{    
    @GET
    @Path("{id_nilai}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map getPersentasiByID(@PathParam("id_nilai") Integer id_nilai){
        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("Pesan", "Berhasil");
        System.out.println("id_nilai "+id_nilai);
        try {
            createConnection();
            MyMap persentasiNilai =(MyMap) jt.queryObject("select * from tb_persentasiNilai where id_nilai = ?", new Object[] {id_nilai}, new MyMap());
            closeConnection();
            if(persentasiNilai != null){
                rs.put("result", persentasiNilai);
            }
        } catch (Exception e) {
            rs.put("Pesan", "Gagal karena : " +e.getMessage());
        }
        return rs;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertPersentasiNilai")
    public Object createPersentasi(@Context HttpServletRequest hsr) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        JSONObject request = null;
        MyMap respon = new MyMap();
        MyMap data = new MyMap();
        DataInputStream in;
        try {
            createConnection();
            in = new DataInputStream(new BufferedInputStream(hsr.getInputStream()));

            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());
            request = (JSONObject) json.get("request");

            if (request == null) {
                respon.put("pesan", "data yang dikirim tidak ditemukan");
                respon.put("rCode", "99");
                respon.put("id_nilai", "0");
                return respon;
            }
            data.put("kode_matkul", request.getString("kode_matkul"));
            data.put("uts", request.getString("uts"));
            data.put("uas", request.getString("uas"));
            data.put("tugas", request.getString("tugas"));
            data.put("praktikum", request.getString("praktikum"));
            data.put("kehadiran", request.getString("kehadiran"));

            jt.insert("id_nilai", data);
            respon.put("pesan", "data berhasil disimpan");
            respon.put("rCode", "00");
            respon.put("id_nilai", "1");
        } catch (Exception e) {
            e.printStackTrace();
            respon.put("pesan", e.getMessage());
            respon.put("rCode", "99");
            respon.put("id_nilai", "0");
        }
        return respon;
    }
    
}
