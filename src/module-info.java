/**
 * 
 */
/**
 * 
 */
module Bolnica {
	requires java.desktop;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;

    opens entities to org.hibernate.orm.core;
}