btrs
====

Business Trip Report System


Tips:

for development:
web.xml - remove <!-- Production -->

build-dev.properties - set "debug=true"

persistence.xml - 
	<property name="hibernate.hbm2ddl.auto" value="update"/>
	<property name="hibernate.show_sql" value="true"/>
	<property name="hibernate.format_sql" value="true"/>
	remove <property name="hibernate.jdbc.batch_size" value="20"/>
	
	
VM:
ip: 192.168.0.138
root: f00@bar


JBoss
location: /usr/local/jboss-5.1.0.GA/
./run.sh -b 0.0.0.0
service jboss restart
when deploying, copy "btrs-ds.xml" file and "btrs.war" folder
install: http://easylinuxstuffs.blogspot.tw/2009/08/installing-jboss-on-linux.html


PostgreSQL
location: /var/lib/pgsql/9.2/
service postgresql-9.2 restart
install: http://www.postgresql.org/download/linux/redhat/

max_prepared_transactions = 20 (for using separate db for users)