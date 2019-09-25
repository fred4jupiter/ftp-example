# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|  
  config.vm.box = "ubuntu/bionic64"
  
  config.vm.network "forwarded_port", guest: 22, host: 22
  config.vm.network "forwarded_port", guest: 21, host: 21

  config.vm.synced_folder ".", "/vagrant", type: "virtualbox"
  config.vm.synced_folder "./.vagrant", "/vagrant/.vagrant", mount_options: ["dmode=700,fmode=600"]

  config.vm.network "private_network", ip: "192.168.75.75"  
 
  config.vm.provider "virtualbox" do |vb|
    vb.name = "ftp-server"
    vb.customize ["modifyvm", :id, "--memory", "1024"]
  end  
 
  # to prevent tty errors
  config.ssh.shell = "bash -c 'BASH_ENV=/etc/profile exec bash'"  
  config.vm.provision "shell", inline: "sudo apt update -y && sudo apt install vsftpd -y"
  config.vm.provision "shell", inline: "sudo sed -i '/#write_enable=YES/c write_enable=YES' /etc/vsftpd.conf"
end
