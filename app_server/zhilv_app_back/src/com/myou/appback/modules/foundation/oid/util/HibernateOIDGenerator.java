package com.myou.appback.modules.foundation.oid.util;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * Description: 20位OID的 Hibernate生成器<br>
 * @version 1.0
 */
public class HibernateOIDGenerator implements IdentifierGenerator {

    public HibernateOIDGenerator() {

    }

    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        return  OIDGenerator.getOID(session.connection());
    }

}
