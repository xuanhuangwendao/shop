package com.xing.shop.util;

import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import java.security.PrivateKey;
import java.util.UUID;

/**
 * @author ：xuanhuangwendao
 * @date ：Created in 2022/3/19 1:23
 */
public class JwtUtils {

    /**
     * keyId,公钥,私钥 都是用 createKeyPair 方法生成
     */
    private static final String keyId = "e281e0aa4b7348fea1ff88879a86a6e2";
    private static final String privateKeyStr = "{\"kty\":\"RSA\",\"kid\":\"e281e0aa4b7348fea1ff88879a86a6e2\",\"alg\":\"RS256\",\"n\":\"1o5zPMw86wRRKd598rUYZBLqKUQ0PS9AAA3AHnVY7sws8sEbtO99byLnw7pKksafLrL-6aOppqKGY44sxeF4ps2OcQBLTyH1hcF8zttviMCy9j9BJMlVT_TMGtBR_t-CzgvDRCYNAak-P6Ly6GS3zul5r_GPYsK43OZ5f7UCjunCw_c0aENV84FAjCSjuRa-9KMsxn1YHID1c47bh8etPjevKOgrMYm7pXVdD1fx09JmbZQhnmqVCqY4YwdADkBcBLjHbW65a46y1KyA8alDrIHic2f7HaVzbJWLx2vNbk2DFGrolADpNahzSOyPWGgMoUXaQVquWMjVbUHCC_yHQQ\",\"e\":\"AQAB\",\"d\":\"AUeXeJv7u4ear9IdxHAv0W3680zOtnQ1EKMEW7IlugPh-z8rWgKJKBvoT7apWfOBEMtcwZo6KW4j-M1I51O7T1xfvn0K1fGdCCORqd6tbT5_-r_BFNX2EixiuMC4Enn1CuNPUnVbI8DFiCXhxsDOB1gFY6cgTK2eYmPmAtzao21O8eMvOUJVvk2CgU_yyCFXeg5csmIYkN5doOv-17nSnJ0R_W6QLU0-WQHIdwPP9OcQTJHHNrDcJdPLXyIZNYqiNK311AOss5T2scVyg6cefVarm0PjfgbNYD0slPp8n1l7DlgeRjbt88iCVivSrii5YemWjoxTMo3I8DanwhMfoQ\",\"p\":\"8hvEv4Lg1R060bt-4KUUePcrENa_2OncQ0JS8sThIvrPXMgy95BIdt4yFjZg5xFKMlodmzuxplAqsxLbHttYbCFfCLR6rM9z-3mJaY1PvAi3JZk8ZfI5wfFpO194KNI6dnS3kEB3oDoibCx6NhP6w-zNMHJhtGwkeic-pzGfRdE\",\"q\":\"4t37NIDQH1MKDvuTm5EFj1Qb16l4RS1I7J8E4nnuGKw-F5hswmnh9XLFi-mQ-MWhNGXvVwErfTga808Lu0OL1ne_6SWCB7GHAoU0wGINTLkN_KtYW__reHlrzAIQOwaZNdYHQ3pKODlb3ozo_YFmImIhvXMH4Qi78IToMGWy1nE\",\"dp\":\"sgdHSsfzieJfgR_NMWVSQ40E5vHyyo9Uv0SC-C9bmbwyRPiVucMn9fh3fivoX6EwHQx03tQrUq96ZomEBRGn2INFfYIw_ufhUlbRe4NE0XDbM9OVPSW74OgiyFzwX5wPD1HONU2iaXi2HTMrfp58dBup2mkRfXlT7Vr0UD9F7jE\",\"dq\":\"DvNwHaqrjJTtYcVkmej5w9NYLmNFwNFWL_ne2YoNjeS0BtwNttZBhOp2aF0RJ8PmdsyM9F_oXM04wXYhIMs3yP-FTzkgk584dYsPyensVHURnegw34dDHmJqHzy3kxxBNpIPUnwZrhDhpA4HQMWpVHTBInoVN9hagmRP5Piey5E\",\"qi\":\"Ji4pnjfylBSzsxyfctTZZmAmGRDjbub6fwf19AyiobBx68pVFao-Z5e6PEag87NjP_PcjQqMpCome9nDX9nPZqi1TjOJR459urtwsU4dJkj6qSEPBbN-1rx_Xy_-DkzIW6eMbcW8MaWxhXvS4oZP7DI17oOSn8zpqaKowotCxGI\"}";
    private static final String publicKeyStr = "{\"kty\":\"RSA\",\"kid\":\"e281e0aa4b7348fea1ff88879a86a6e2\",\"alg\":\"RS256\",\"n\":\"1o5zPMw86wRRKd598rUYZBLqKUQ0PS9AAA3AHnVY7sws8sEbtO99byLnw7pKksafLrL-6aOppqKGY44sxeF4ps2OcQBLTyH1hcF8zttviMCy9j9BJMlVT_TMGtBR_t-CzgvDRCYNAak-P6Ly6GS3zul5r_GPYsK43OZ5f7UCjunCw_c0aENV84FAjCSjuRa-9KMsxn1YHID1c47bh8etPjevKOgrMYm7pXVdD1fx09JmbZQhnmqVCqY4YwdADkBcBLjHbW65a46y1KyA8alDrIHic2f7HaVzbJWLx2vNbk2DFGrolADpNahzSOyPWGgMoUXaQVquWMjVbUHCC_yHQQ\",\"e\":\"AQAB\"}";

    public static long accessTokenExpirationTime = 60 * 60 * 24;

    //jws创建token
    public static String createToken(String userId) {
        try {
            JwtClaims claims = new JwtClaims();
            claims.setGeneratedJwtId();
            claims.setIssuedAtToNow();
            NumericDate date = NumericDate.now();
            date.addSeconds(accessTokenExpirationTime);
            claims.setExpirationTime(date);
            claims.setNotBeforeMinutesInThePast(1);
            claims.setSubject("xing");
            claims.setAudience("shop");
            claims.setClaim("userId", userId);
            JsonWebSignature jws = new JsonWebSignature();
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            jws.setKeyIdHeaderValue(keyId);
            jws.setPayload(claims.toJson());
            PrivateKey privateKey = new RsaJsonWebKey(JsonUtil.parseJson(privateKeyStr)).getPrivateKey();
            jws.setKey(privateKey);
            String idToken = jws.getCompactSerialization();
            return idToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * jws校验token
     *
     * @param token
     * @return 返回 用户账号
     * @throws JoseException
     */
    public static String verifyToken(String token) {
        try {
            JwtConsumer consumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime()
                    .setMaxFutureValidityInMinutes(5256000)
                    .setAllowedClockSkewInSeconds(30)
                    .setRequireSubject()
                    .setExpectedAudience("shop")
                    .setVerificationKey(new RsaJsonWebKey(JsonUtil.parseJson(publicKeyStr)).getPublicKey())
                    .build();

            JwtClaims claims = consumer.processToClaims(token);
            if (claims != null) {
                String userId = (String) claims.getClaimValue("userId");
                return userId;
            }
        }  catch (JoseException e) {
            e.printStackTrace();
        }  catch (InvalidJwtException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建jwk keyId , 公钥 ，秘钥
     */
    public static void createKeyPair(){
        String keyId = UUID.randomUUID().toString().replaceAll("-", "");
        RsaJsonWebKey jwk = null;
        try {
            jwk = RsaJwkGenerator.generateJwk(2048);
        } catch (JoseException e) {
            e.printStackTrace();
        }
        jwk.setKeyId(keyId);
        jwk.setAlgorithm(AlgorithmIdentifiers.RSA_USING_SHA256);
        String publicKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        String privateKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
        System.out.println("keyId="+keyId);
        System.out.println();
        System.out.println("公钥 publicKeyStr="+publicKey);
        System.out.println();
        System.out.println("私钥 privateKeyStr="+privateKey);
    }

    public static void main(String[] args){
        createKeyPair();
    }
}
