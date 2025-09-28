package com.github.ceredira;

import com.github.ceredira.repository.LocalPackageRepository;
import com.github.ceredira.repository.RemotePackageRepository;
import com.github.ceredira.utils.DependencyResolver;
import com.github.ceredira.utils.Utils;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LocalPackageRepository localRepo = new LocalPackageRepository();
        RemotePackageRepository remoteRepo = new RemotePackageRepository();
        DependencyResolver resolver = new DependencyResolver();

//        PackageManager pm = new PackageManager(localRepo, remoteRepo, resolver);
//
//        pm.install("myapp");
//        pm.upgrade("myapp");

    }
}