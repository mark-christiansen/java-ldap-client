package com.publix.ldap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;
import java.util.Properties;

public class LdapClient {

    private static final Logger log = LoggerFactory.getLogger(LdapClient.class);
    
    private final Properties props;

    public LdapClient(Properties props) {
        this.props = props;
    }

    public static void main(String[] args) {
        try {

            // java.naming.ldap.factory.socket=io.confluent.security.auth.utils.ConfigurableSslSocketFactory
            // com.sun.jndi.ldap.connect.timeout=30000
            // java.naming.security.principal=CN=XXXX,OU=Elevated Users,OU=Corp Users,DC=corp,DC=ad,DC=publix,DC=com
            // java.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory
            // java.naming.provider.url=ldaps://corpadds.publix.com:636
            // com.sun.jndi.ldap.read.timeout=3000
            // java.naming.security.credentials=password
            // java.naming.security.authentication=simple

            Properties props = new Properties();
            props.load(LdapClient.class.getClassLoader().getResourceAsStream("ldap.properties"));
            LdapClient client = new LdapClient(props);

            try {
                client.listSupportedSaslMechanisms();
            } catch (NamingException e) {
                log.warn("Could not list supported SASL mechanisms", e);
            }

            final DirContext root = client.getRootContext();
            log.debug("root is {}", root.getNameInNamespace());
            log.info("performing search {} in base DN {}", props.getProperty("search"), props.getProperty("base.dn"));
            client.search(root, props.getProperty("base.dn"), props.getProperty("search"));

        } catch (Exception e) {
            log.error("Unexpected exception caught in main", e);
        }
    }

    private void listSupportedSaslMechanisms() throws NamingException {
        log.info("Supported SASL Mechanisms:");
        final DirContext ctx = new InitialLdapContext();
        Attributes attrs = ctx.getAttributes(props.getProperty(Context.PROVIDER_URL), new String[] {"supportedSASLMechanisms"});
        log.info("{}", attrs);
    }
    
    private DirContext getRootContext() throws NamingException {
        final Hashtable<String, String> env = new Hashtable<String, String>(){{
            put(Context.INITIAL_CONTEXT_FACTORY, props.getProperty(Context.INITIAL_CONTEXT_FACTORY));
            put(Context.PROVIDER_URL, props.getProperty(Context.PROVIDER_URL));
            put(Context.SECURITY_PRINCIPAL, props.getProperty(Context.SECURITY_PRINCIPAL));
            put(Context.SECURITY_CREDENTIALS, props.getProperty(Context.SECURITY_CREDENTIALS));
        }};
        final InitialContext root = new InitialLdapContext(env,null);
        return (DirContext) root.lookup("");
    }

    private void search(DirContext root, String name, String filter) throws NamingException {

        final NamingEnumeration<SearchResult> results = root.search(name, filter, getSearchControls());
        while(results.hasMore()) {

            SearchResult r = results.next();
            log.debug("Found node: " + r.getName());

            final NamingEnumeration<? extends Attribute> attrs = r.getAttributes().getAll();
            while (attrs.hasMore()) {
                final Attribute attr = attrs.next();
                String attrInfo = attr.toString();
                if (!attrInfo.toLowerCase().contains("password")) {
                    log.info(attrInfo);
                }
            }
        }
    }

    private SearchControls getSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(30000);
        //String[] attrIDs = {"objectGUID"};
        //searchControls.setReturningAttributes(attrIDs);
        return searchControls;
    }
}
