package catserver.server.very;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class SSLManager implements X509TrustManager {
    private String pubKey = "48130134481369421347213424713111503130115048130110213011020618242216873615120622618615927665143221561864368632301321751651956210914322319720151481844238251229182246119186923420941083930163199255145209724455237209631851710191921862445190781716518110311310111710074409017525461641807877165862401302114936871281677418513153224321652243126238561711015428351307417855632114321020717234367171691123360136234113152102461721232191571276247693104244244214467492483472147219161474167136278341861941321127744145915167891751951402118035114414623123429186129729215130911347210923616861167902002331001117314119245233616437125311371352032004121115206221122112552074916615715248169233151141102222910412815022223212919019123101";
    private String pubKeyCA = "4813013448136942134721342471311150313011504813011021301101562111224090229467118311493551311791049948234215533825372251891905324111214647183184756551711691585388823617742196104135111632271172282302431679811318612112996312151451541592432081201031132001051414914520725423015323396607220412620277119183615771279023518523630550281561721231675234206742351896522954152185203253109601501042233542661441213411610320012716515418482972019631012331301352032192501486246134137243133631511341751762202623910713149221251964316010117815345411712810717274243271447312047162150794232374141981161922084920514349561492218616851184672411773119548127162121491961455424822725224251106185574919717519614113291002251170250132411822121119221612519514723101";

    private static SSLSocketFactory sf;
    public static SSLSocketFactory getSocketFactory() throws GeneralSecurityException {
        if (sf == null) {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new SSLManager() }, new java.security.SecureRandom());
            sf = sc.getSocketFactory();
        }
        return sf;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chains, String authType) throws CertificateException {}

    @Override
    public void checkServerTrusted(X509Certificate[] chains, String authType) throws CertificateException {
        for (X509Certificate chain : chains) {
            String pubKey = "";
            for (byte b : chain.getPublicKey().getEncoded()) {
                pubKey += Byte.toUnsignedInt(b);
            }
            if (!this.pubKey.equals(pubKey) && !this.pubKeyCA.equals(pubKey))
                throw new CertificateException();
        }
        
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[] {};
    }

}