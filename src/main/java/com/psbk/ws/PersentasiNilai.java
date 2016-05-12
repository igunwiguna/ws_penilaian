
package com.psbk.ws;

public class PersentasiNilai {
    private int id_nilai;
    private String kode_matkul;
    private float uts,uas,tugas,praktikum,kehadiran;

    public int getId_nilai() {
        return id_nilai;
    }

    public void setId_nilai(int id_nilai) {
        this.id_nilai = id_nilai;
    }

    public String getKode_matkul() {
        return kode_matkul;
    }

    public void setKode_matkul(String kode_matkul) {
        this.kode_matkul = kode_matkul;
    }

    public float getUts() {
        return uts;
    }

    public void setUts(float uts) {
        this.uts = uts;
    }

    public float getUas() {
        return uas;
    }

    public void setUas(float uas) {
        this.uas = uas;
    }

    public float getTugas() {
        return tugas;
    }

    public void setTugas(float tugas) {
        this.tugas = tugas;
    }

    public float getPraktikum() {
        return praktikum;
    }

    public void setPraktikum(float praktikum) {
        this.praktikum = praktikum;
    }

    public float getKehadiran() {
        return kehadiran;
    }

    public void setKehadiran(float kehadiran) {
        this.kehadiran = kehadiran;
    }

    
    
    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Persentasi Nilai : "+id_nilai+" - "+kode_matkul+" - "+uts+" - "+uas+" - "+tugas+" - "+praktikum+" - "+kehadiran;
	}

    
}
